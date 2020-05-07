package com.example.myapplication

import android.Manifest
import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.view.CameraView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.myapplication.db.Note
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.*


class CameraActivity : AppCompatActivity(), ImageCapture.OnImageSavedListener {



    private var cameraView: CameraView? = null
    private var takePictureButton: View? = null

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
        cameraView = findViewById<View>(R.id.cameraView) as CameraView?
        cameraView!!.setCaptureMode(CameraView.CaptureMode.IMAGE)
        cameraView!!.bindToLifecycle(this as LifecycleOwner)

        takePictureButton = findViewById<View>(R.id.takePictureButton)
        takePictureButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                cameraView!!.takePicture(
                    generatePictureFile(),
                    AsyncTask.SERIAL_EXECUTOR,
                    this@CameraActivity
                )
            }
        })
    }
    override fun onError(
        imageCaptureError: ImageCapture.ImageCaptureError,
        message: String,
        cause: Throwable?
    ) {
        finish()
    }



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

        var resultText = "Demo"
            val result = detector.processImage(image)
                /*.addOnSuccessListener { firebaseVisionText ->
                resultText = firebaseVisionText.text
                // Task completed successfully
            }*/
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    e.printStackTrace()
                }
        //TODO: Make asycn waiting
        while(!result.isComplete){
        }
        resultText = result.getResult()?.text.toString()

        val index = App.noteRepository.createNote(Note(
            1,
            Date(1576174880000),
            resultText,
            file.absolutePath
        ))
        var data = intent
        data.putExtra(INDEX_RESULT_KEY, index);
        setResult(Activity.RESULT_OK, data);

        finish()
    }


    private fun generatePictureFile(): File {
        return File(filesDir, UUID.randomUUID().toString())
    }

    companion object {
        private val CAMERA_REQUEST_CODE = 0
        public val INDEX_RESULT_KEY = "id"
    }
}