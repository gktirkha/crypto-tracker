package `in`.gtirkha.cryptotracker.modals

import org.json.JSONObject

class CurrencyModal(val name: String, val symbol: String, val price: String) {

    companion object {
        fun fromJson(json: JSONObject): CurrencyModal {
            val name = json.getString("name")
            val symbol = json.getString("symbol")
            var price = String.format(
                "$%.2f", json.getJSONObject("quote").getJSONObject("USD").getDouble("price")
            )
            return CurrencyModal(name, symbol, price)
        }
    }
}