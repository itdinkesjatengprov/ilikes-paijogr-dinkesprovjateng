package go.id.dinkesjatengprov.ilikes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import go.id.dinkesjatengprov.ilikes.R

class AddressAutoCompleteAdapter(context: Context, var list: ArrayList<String>?) :
    ArrayAdapter<String>(context, R.layout.item_autocomplete, list ?: ArrayList()) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.item_autocomplete, parent, false)

        val textView = view.findViewById<TextView>(R.id.isa_tv_text)
        textView.text = list?.get(position)

        return view
    }

    override fun getItem(position: Int): String {
        return list?.get(position) ?: String()
    }

    override fun getCount(): Int {
        return list?.size ?: 0
    }
}