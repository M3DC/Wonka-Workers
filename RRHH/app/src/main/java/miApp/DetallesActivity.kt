package miApp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.rrhh.R
import com.squareup.picasso.Picasso

class DetallesActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oompa_loompa_detail)

        // Datos del Intent
        val image = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        val lastName = intent.getStringExtra("last_name")
        val profession = intent.getStringExtra("profession")
        val gender = intent.getCharExtra("gender", '-')
        val email = intent.getStringExtra("email")
        val age = intent.getIntExtra("age", 0).toString()
        val country = intent.getStringExtra("country")
        val height = intent.getIntExtra("height", 0).toString()
        val favoriteDetails = intent.getStringExtra("favorite")

        // Layout ids
        val nameTextView = findViewById<TextView>(R.id.textViewName)
        val lastNameTextView = findViewById<TextView>(R.id.textViewLastName)
        val professionTextView = findViewById<TextView>(R.id.textViewProfession)
        val genderTextView = findViewById<TextView>(R.id.textViewGender)
        val emailTextView = findViewById<TextView>(R.id.emailTextView)
        val ageTextView = findViewById<TextView>(R.id.ageTextView)
        val countryTextView = findViewById<TextView>(R.id.countryTextView)
        val heightTextView = findViewById<TextView>(R.id.heightTextView)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val favoriteTextView = findViewById<TextView>(R.id.favoriteTextView)

        Picasso.get()
            .load(image)
            .fit()
            .centerCrop()
            .into(imageView)

        // Valores de los TextView con los datos
        nameTextView.text = "Name: $name"
        lastNameTextView.text = "Last Name: $lastName"
        professionTextView.text = "Profession: $profession"
        genderTextView.text = "Gender: $gender"
        emailTextView.text = "Email: $email"
        ageTextView.text = "Age: $age"
        countryTextView.text = "Country: $country"
        heightTextView.text = "Height: $height"

        // Mostrar los detalles favoritos en un TextView
        val favoriteArray = favoriteDetails?.split("\n")
        val favoriteStringBuilder = StringBuilder()
        if (!favoriteArray.isNullOrEmpty()) {
            for (detail in favoriteArray) {
                favoriteStringBuilder.append(detail.trim()).append("\n\n")
            }
        }
        favoriteTextView.text = favoriteStringBuilder.toString().trim()

        //Boton para regresar
        val backButton = findViewById<Button>(R.id.buttonBack)
        backButton.setOnClickListener {
            finish()
        }
    }
}
