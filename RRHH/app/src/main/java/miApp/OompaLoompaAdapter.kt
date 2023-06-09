package miApp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rrhh.R
import com.squareup.picasso.Picasso
import android.widget.Button

class OompaLoompaAdapter(private val context: Context) : RecyclerView.Adapter<OompaLoompaAdapter.ViewHolder>() {

    private val oompaLoompas: MutableList<OompaLoompa> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_oompa_loompa, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val oompaLoompa = oompaLoompas[position]
        Picasso.get()
            .load(oompaLoompa.image)
            .fit()
            .centerCrop()
            .into(holder.imageView)

        holder.nameTextView.text = oompaLoompa.fullName
        holder.detailsButton.visibility = View.VISIBLE

        // Listener del botón de detalles
        holder.detailsButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetallesActivity::class.java)
            intent.putExtra("image", oompaLoompa.image)
            intent.putExtra("name", oompaLoompa.first_name)
            intent.putExtra("last_name", oompaLoompa.last_name)
            intent.putExtra("profession", oompaLoompa.profession)
            intent.putExtra("gender", oompaLoompa.gender)
            intent.putExtra("email", oompaLoompa.email)
            intent.putExtra("age", oompaLoompa.age)
            intent.putExtra("country", oompaLoompa.country)
            intent.putExtra("height", oompaLoompa.height)

            val favorite = "color: ${oompaLoompa.favorite.color}\n" +
                    "food: ${oompaLoompa.favorite.food}\n" +
                    "random_string: ${oompaLoompa.favorite.random_string}\n" +
                    "song: ${oompaLoompa.favorite.song}"

            intent.putExtra("favorite", favorite)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return oompaLoompas.size
    }

    //Añadir o limpiar datos
    fun setData(data: List<OompaLoompa>) {
        oompaLoompas.clear()
        oompaLoompas.addAll(data)
        notifyDataSetChanged()
    }

    // ViewHolder
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val detailsButton: Button = view.findViewById(R.id.detailsButton)
    }
}


