package com.alexius.mygooglemaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.alexius.mygooglemaps.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    //STEPS TO GET API FOR GOOGLE MAPS
    //1. Pertama, masuk ke Google Cloud Console dan buka tab Credential.
    //2. Kemudian masuk ke menu samping dan pilih API & Services → Credentials
    //3. Buat project baru dengan cara klik CREATE PROJECT dan ubah nama project sesuai keinginan Anda. Klik CREATE untuk melanjutkan.
    //4. Selanjutnya aktifkan fitur Google Maps dengan cara pilih Enabled APIs & Services pada menu samping dan klik tombol + ENABLED APIS AND SERVICES.
    //5. Akan tampil berbagai macam fitur yang bisa Anda gunakan di Google Cloud Console. Pilihlah Maps SDK for Android dan klik Enable.
    //6. Selanjutnya pilih menu Credentials pada menu samping dan klik tombol CREATE CREDENTIALS → API key untuk membuat kredensial baru.
    //7. Sampai sini, Anda sudah mendapat key yang biasanya diawali dengan kata “AIza…”. Sebenarnya Anda sudah bisa menggunakan API key ini, tetapi key ini masih kurang aman karena project apapun bisa menggunakannya. Supaya lebih aman klik link Edit API key.
    //8. Pilih checkbox Android apps pada Application restrictions dan tambahkan data baru dengan klik ADD AN ITEM. Kemudian isikan dengan nama package aplikasi Anda dan SHA-1 dari device yang digunakan
    //9. Setiap device memiliki SHA certificate yang berbeda-beda. Untuk mendapatkan SHA-1, Anda dapat menjalankan perintah gradlew signingreport atau ./gradlew signingReport pada Terminal Android Studio.
    //10. Selain menggunakan cara di atas, Anda juga dapat membuat APIKey dengan pembatasan akses melalui direct link seperti ini.
    // https://console.developers.google.com/flows/enableapi?apiid=maps-android-backend.googleapis.com&keyType=CLIENT_SIDE_ANDROID&r=11:22:33:44:55:66:77:88:99:00:AA:BB:CC:DD:EE:FF:01:23:45:67%3Bcom.namapackage.namaaplikasi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}