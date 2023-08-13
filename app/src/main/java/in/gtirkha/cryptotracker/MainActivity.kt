package `in`.gtirkha.cryptotracker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import `in`.gtirkha.cryptotracker.adapters.AdapterCurrencyTile
import `in`.gtirkha.cryptotracker.databinding.ActivityMainBinding
import `in`.gtirkha.cryptotracker.modals.CurrencyModal
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var data: ArrayList<CurrencyModal>
    private lateinit var rvAdapter: AdapterCurrencyTile
    override fun onCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = android.graphics.Color.parseColor("#363535")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initialization
        data = ArrayList()
        rvAdapter = AdapterCurrencyTile(this, data, binding)
        binding.rvCurrency.layoutManager = LinearLayoutManager(this)
        binding.rvCurrency.adapter = rvAdapter
        //call api
        apiData
        binding.searchField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val filteredData = ArrayList<CurrencyModal>()
                for (item in data) {
                    if (item.name.lowercase()
                            .contains(p0.toString().lowercase()) || item.symbol.lowercase()
                            .contains(p0.toString().lowercase())
                    ) {
                        filteredData.add(item)
                    }
                }
                if (filteredData.isNotEmpty()) {
                    rvAdapter.changeData(filteredData)
                }
            }
        })
    }

    private val apiData: Unit
        get() {
            val url = BuildConfig.API_URL
            val queue = Volley.newRequestQueue(this)
            val jsonRequestObject: JsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener { response ->
                    try {
                        val dataArray = response.getJSONArray("data")
                        for (i in 0 until dataArray.length()) {
                            data.add(CurrencyModal.fromJson(dataArray.getJSONObject(i)))
                            rvAdapter.notifyItemInserted(i)
                        }
                        binding.progressBar.isVisible = false
                        binding.rvCurrency.isVisible = true


                    } catch (e: Exception) {
                        println("response")
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val header = HashMap<String, String>()
                        header["X-CMC_PRO_API_KEY"] = BuildConfig.API_KEY
                        return header
                    }

                    override fun parseNetworkResponse(response: NetworkResponse?): Response<JSONObject> {
                        return super.parseNetworkResponse(response)
                    }
                }
            queue.add(jsonRequestObject)
        }
}