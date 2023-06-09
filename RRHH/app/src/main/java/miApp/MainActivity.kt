package miApp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rrhh.R

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OompaLoompaAdapter
    private lateinit var pageNumberTextView: TextView
    private lateinit var genderRadioGroup: RadioGroup
    private lateinit var professionSpinner: Spinner

    private val apiService = OompaLoompaApiService()
    private var currentPage = 1
    private var totalPages = 20
    private var selectedGender: String = "All"
    private var allProfessions: Set<String> = HashSet()
    private var selectedProfession: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = OompaLoompaAdapter(this)
        recyclerView.adapter = adapter

        val exitButton = findViewById<Button>(R.id.buttonExit)
        val allButton = findViewById<Button>(R.id.allButton)
        val prevPageButton = findViewById<Button>(R.id.prevPageButton)
        val nextPageButton = findViewById<Button>(R.id.nextPageButton)
        pageNumberTextView = findViewById(R.id.pageNumberTextView)
        genderRadioGroup = findViewById(R.id.genderRadioGroup)
        professionSpinner = findViewById(R.id.professionSpinner)

        genderRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            selectedGender = selectedRadioButton.text.toString()
            filterOompaLoompas()
        }

        exitButton.setOnClickListener {
            showToast("Cerrando Wonka Workers")
            finishAffinity()
        }

        allButton.setOnClickListener {
            selectedGender = "All"
            showToast("Oompas refrescados")
            filterOompaLoompas()
        }

        prevPageButton.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                fetchOompaLoompas(currentPage)
                updatePageNumber()
                updateButtonVisibility()
            }
        }

        nextPageButton.setOnClickListener {
            if (currentPage < totalPages) {
                currentPage++
                fetchOompaLoompas(currentPage)
                updatePageNumber()
                updateButtonVisibility()
            }
        }

        fetchAllProfessions()
        setupProfessionSpinner()

        updatePageNumber()
        updateButtonVisibility()
    }

    //Se consiguen las profesiones
    private fun fetchAllProfessions() {
        Thread {
            try {
                val professions = HashSet<String>()
                for (page in 1..totalPages) {
                    val oompaLoompas = apiService.getOompaLoompas(page)
                    oompaLoompas?.forEach { oompaLoompa ->
                        professions.add(oompaLoompa.profession)
                    }
                }
                allProfessions = professions
                runOnUiThread {
                    setupProfessionSpinner()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    //Se colocan las profesiones en el spinner
    private fun setupProfessionSpinner() {
        val professionList = ArrayList(allProfessions)
        professionList.sort()
        professionList.add(0, "All Professions")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, professionList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        professionSpinner.adapter = adapter

        professionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedProfession = professionSpinner.selectedItem as String
                filterOompaLoompas()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                showToast("Nada seleccionado")
            }
        }
    }

    //se filtran oompaloompas
    private fun filterOompaLoompas() {
        currentPage = 1
        fetchOompaLoompas(currentPage)
        updatePageNumber()
        updateButtonVisibility()
    }

    //Se consiguen los oompaloompas
    private fun fetchOompaLoompas(page: Int) {
        Thread {
            try {
                val oompaLoompas = apiService.getOompaLoompas(page)
                if (oompaLoompas != null && oompaLoompas.isNotEmpty()) {
                    val filteredData = oompaLoompas.filter { oompaLoompa ->
                        (selectedGender.equals("All", ignoreCase = true) || oompaLoompa.gender.toString().equals(
                            selectedGender,
                            ignoreCase = true
                        )) && (selectedProfession.equals("All Professions", ignoreCase = true) || oompaLoompa.profession.equals(
                            selectedProfession,
                            ignoreCase = true
                        ))
                    }.map { oompaLoompa ->
                        OompaLoompa(
                            oompaLoompa.first_name,
                            oompaLoompa.last_name,
                            oompaLoompa.favorite,
                            oompaLoompa.gender,
                            oompaLoompa.image,
                            oompaLoompa.profession,
                            oompaLoompa.email,
                            oompaLoompa.age,
                            oompaLoompa.country,
                            oompaLoompa.height,
                            oompaLoompa.id
                        )
                    }
                    runOnUiThread {
                        adapter.setData(filteredData)
                        checkEmptyList(filteredData)
                    }
                } else {
                    runOnUiThread {
                        adapter.setData(emptyList())
                        checkEmptyList(emptyList())
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun checkEmptyList(data: List<OompaLoompa>) {
        if (data.isEmpty()) {
            showToast("No se encontro nada")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updatePageNumber() {
        pageNumberTextView.text = "Page $currentPage of $totalPages"
    }

    private fun updateButtonVisibility() {
        val prevPageButton = findViewById<Button>(R.id.prevPageButton)
        val nextPageButton = findViewById<Button>(R.id.nextPageButton)

        prevPageButton.isEnabled = currentPage > 1
        nextPageButton.isEnabled = currentPage < totalPages
    }
}




