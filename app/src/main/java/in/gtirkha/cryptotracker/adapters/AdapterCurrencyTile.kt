package `in`.gtirkha.cryptotracker.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import `in`.gtirkha.cryptotracker.databinding.ActivityMainBinding
import `in`.gtirkha.cryptotracker.databinding.RvCurrencyItemBinding
import `in`.gtirkha.cryptotracker.modals.CurrencyModal

class AdapterCurrencyTile(
    private val context: Context,
    private var data: ArrayList<CurrencyModal>,
    private var mainBinding: ActivityMainBinding
) : RecyclerView.Adapter<AdapterCurrencyTile.ViewHolder>() {


    inner class ViewHolder(val binding: RvCurrencyItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RvCurrencyItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setAnimation(holder.itemView)
        holder.binding.name.text = data[position].name
        holder.binding.price.text = data[position].price
        holder.binding.symbol.text = data[position].symbol
        holder.itemView.setOnClickListener {
            mainBinding.searchField.clearFocus()
        }
    }

    private fun setAnimation(view: View) {
        val anim = AlphaAnimation(0f, 1f)
        anim.duration = 750
        view.startAnimation(anim)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun changeData(filteredData: ArrayList<CurrencyModal>) {
        data = filteredData
        this.notifyDataSetChanged()
    }

}