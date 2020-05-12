package com.example.myapplication

import android.Manifest
import android.Manifest.permission
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.view.CameraView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


class CameraActivity : AppCompatActivity(), ImageCapture.OnImageSavedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera)

        if (ContextCompat.checkSelfPermission(
                this,
                permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.CAMERA
                    )
                ) {
                    Toast.makeText(this,R.string.need_permission, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, R.string.permission_in_settings, Toast.LENGTH_SHORT).show()
                }
                finish()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun startCamera() {
        val cameraView = findViewById<View>(R.id.cameraView) as CameraView?
        cameraView!!.captureMode = CameraView.CaptureMode.IMAGE
        cameraView.bindToLifecycle(this as LifecycleOwner)

        val takePictureButton = findViewById<View>(R.id.takePictureButton)
        takePictureButton!!.setOnClickListener {
            cameraView.takePicture(
                generatePictureFile(),
                AsyncTask.SERIAL_EXECUTOR,
                this@CameraActivity
            )
        }
    }
    override fun onError(
        imageCaptureError: ImageCapture.ImageCaptureError,
        message: String,
        cause: Throwable?
    ) {
        finish()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onImageSaved(file: File) {

        var image: FirebaseVisionImage = FirebaseVisionImage.fromFilePath(this.applicationContext, Uri.fromFile(file))
        try {
            image = FirebaseVisionImage.fromFilePath(this.applicationContext, Uri.fromFile(file))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        FirebaseApp.initializeApp(this);
        val detector = FirebaseVision.getInstance()
            .onDeviceTextRecognizer

        var resultText: String
        detector.processImage(image)
            .addOnSuccessListener { processedText ->
                resultText = processedText.text
                val index = GlobalScope.async (Dispatchers.IO) {
                    App.noteRepository.createNote(
                        Date(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()),
                        resultText,
                        file.absolutePath
                    )
                }
                GlobalScope.launch(Dispatchers.Main) {
                    val data = intent
                    data.putExtra(INDEX_RESULT_KEY, index.await());
                    setResult(Activity.RESULT_OK, data);
                    finish()
                }
            }
            .addOnFailureListener { e ->
                e.printStackTrace()
            }
    }


    private fun generatePictureFile(): File {
        return File(filesDir, UUID.randomUUID().toString())
    }

    companion object {
        private const val CAMERA_REQUEST_CODE = 0
        const val INDEX_RESULT_KEY = "id"
    }
}