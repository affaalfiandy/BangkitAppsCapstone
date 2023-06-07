package com.example.mommymate2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.mommymate2.databinding.ActivityRecordBinding
import android.util.Log
import android.view.View
import android.widget.Toast
import java.io.File
import android.media.AudioRecord
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.github.squti.androidwaverecorder.WaveRecorder
import java.io.BufferedOutputStream
import java.io.FileOutputStream
import java.io.IOException


class RecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordBinding

    private var output: String? = null


    private val REQUIRED_PERMISSION =
        arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private lateinit var mediaRecorder: MediaRecorder


    var path : File = File(Environment.getExternalStorageDirectory().absolutePath+"/myrec/")


    var isPrepareDone = false



    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkNeededPermissions()



        try{
            // create a File object for the parent directory
            val recorderDirectory = File(Environment.getExternalStorageDirectory().absolutePath+"/myrec/")
            // have the object build the directory structure, if needed.
            recorderDirectory.mkdirs()
        }catch (e: IOException){
            e.printStackTrace()
        }

        if (path.exists()){
            output = Environment.getExternalStorageDirectory().absolutePath + "/myrec/rec1" + ".wav"
        }

        mediaRecorder = MediaRecorder()


        binding.navigation.setOnItemSelectedListener { menuItem ->

            when (menuItem.itemId) {
                R.id.navigation_main -> {

                    true
                }

                R.id.navigation_home -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.navigation_setting -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }

        binding.rekamMulai.setOnClickListener {

            binding.rekamOff.visibility = View.GONE
            binding.rekamOn.visibility = View.VISIBLE
            binding.lagiRekam.visibility = View.VISIBLE
            binding.rekamMati.visibility = View.VISIBLE
            binding.rekamMulai.visibility = View.GONE

            Log.d("Recording", "Start recording")



            try {
                println("Starting recording!")
                mediaRecorder?.prepare()

                isPrepareDone = true

                mediaRecorder?.start()

            } catch (e: IllegalStateException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }





        }

        binding.rekamMati.setOnClickListener {
            binding.rekamOff.visibility = View.VISIBLE
            binding.rekamOn.visibility = View.GONE
            binding.lagiRekam.visibility = View.GONE
            binding.rekamMati.visibility = View.GONE
            binding.rekamMulai.visibility = View.VISIBLE

            if (isPrepareDone){
                mediaRecorder?.stop()
                mediaRecorder?.release()
            }

            isPrepareDone = false

            if(path.exists()){
                val count = path.listFiles().size
                output = Environment.getExternalStorageDirectory().absolutePath +"/myrec/rec1"+count+".mp3"
            }

            mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            mediaRecorder?.setOutputFile(output)

        }

        binding.rekamMulai.isEnabled = false



        binding.rekamMulai.isEnabled = true


    }



    private fun allPermissionGranted() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            binding.rekamMulai.isEnabled = true
        } else {

            Toast.makeText(
                this,
                "Tidak dapat menggunakan audio",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }

    private fun checkNeededPermissions() {
        println("Requesting permission")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
            println("Requesting permission")
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO), 0)
        }
    }

    private fun initRecorder() {
        mediaRecorder = MediaRecorder()


    }


    private fun startRecord() {




    }

    private fun stopRecording() {





    }
}



