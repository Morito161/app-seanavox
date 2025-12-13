package com.seavox
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.os.Bundle
import android.view.Surface
import android.view.TextureView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.seavox.databinding.ActivityCamaraBinding

class CamaraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCamaraBinding

    // Camera2 vars
    private lateinit var cameraManager: CameraManager
    private var cameraDevice: CameraDevice? = null
    private var captureSession: CameraCaptureSession? = null
    private var currentCameraId: String? = null

    companion object {
        private const val CAMERA_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCamaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Toast.makeText(this, "Cámara iniciando...", Toast.LENGTH_SHORT).show()

        // ---------------- Menu lateral ----------------
        val drawerLayout = binding.drawerLayout
        binding.btnMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        // Obtener botones del menú
        val headerView = binding.navigationView.getHeaderView(0)
        val btnDiccionario = headerView.findViewById<LinearLayout>(R.id.btnDiccionario)
        val btnHistorial = headerView.findViewById<LinearLayout>(R.id.btnHistorial)
        val btnIdiomas = headerView.findViewById<LinearLayout>(R.id.btnIdiomas)

        btnDiccionario?.setOnClickListener {
            startActivity(Intent(this, DiccionarioActivity::class.java))
            drawerLayout.closeDrawers()
        }

        btnHistorial?.setOnClickListener {
            startActivity(Intent(this, HistorialActivity::class.java))
            drawerLayout.closeDrawers()
        }

        btnIdiomas?.setOnClickListener {
            startActivity(Intent(this, IdiomasActivity::class.java))
            drawerLayout.closeDrawers()
        }

        // Botón traducir
        binding.btnTraducir.setOnClickListener {
            Toast.makeText(this, "Traducción iniciada", Toast.LENGTH_SHORT).show()
        }

        // ---------------- Permisos cámara ----------------
        cameraManager = getSystemService(CAMERA_SERVICE) as CameraManager
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_REQUEST_CODE
            )
        } else {
            startCameraFlow()
        }
    }

    // ---------------- Camera flow ----------------
    private fun startCameraFlow() {
        try {
            currentCameraId = cameraManager.cameraIdList.firstOrNull { id ->
                val chars = cameraManager.getCameraCharacteristics(id)
                chars.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK
            } ?: cameraManager.cameraIdList.first()

            val textureView: TextureView = binding.textureView
            if (textureView.isAvailable) {
                openCamera()
            } else {
                textureView.surfaceTextureListener = surfaceTextureListener
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private val surfaceTextureListener = object : TextureView.SurfaceTextureListener {
        override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) { openCamera() }
        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {}
        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean = true
        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {}
    }

    private fun openCamera() {
        try {
            val camId = currentCameraId ?: return
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) return

            cameraManager.openCamera(camId, stateCallback, null)

        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
    }

    private val stateCallback = object : CameraDevice.StateCallback() {
        override fun onOpened(camera: CameraDevice) {
            cameraDevice = camera
            startPreview()
        }
        override fun onDisconnected(camera: CameraDevice) {
            camera.close()
            cameraDevice = null
        }
        override fun onError(camera: CameraDevice, error: Int) {
            camera.close()
            cameraDevice = null
            Toast.makeText(this@CamaraActivity, "Error al abrir la cámara", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startPreview() {
        try {
            val texture = binding.textureView.surfaceTexture ?: return
            val w = if (binding.textureView.width > 0) binding.textureView.width else 1280
            val h = if (binding.textureView.height > 0) binding.textureView.height else 720
            texture.setDefaultBufferSize(w, h)
            val surface = Surface(texture)

            val requestBuilder = cameraDevice!!.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
            requestBuilder.addTarget(surface)

            cameraDevice!!.createCaptureSession(
                listOf(surface),
                object : CameraCaptureSession.StateCallback() {
                    override fun onConfigured(session: CameraCaptureSession) {
                        captureSession = session
                        requestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
                        session.setRepeatingRequest(requestBuilder.build(), null, null)
                    }
                    override fun onConfigureFailed(session: CameraCaptureSession) {
                        Toast.makeText(this@CamaraActivity, "Fallo al configurar preview", Toast.LENGTH_SHORT).show()
                    }
                },
                null
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_REQUEST_CODE &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCameraFlow()
        } else {
            Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        try {
            captureSession?.close()
            captureSession = null
            cameraDevice?.close()
            cameraDevice = null
        } catch (e: Exception) { e.printStackTrace() }
    }
}
