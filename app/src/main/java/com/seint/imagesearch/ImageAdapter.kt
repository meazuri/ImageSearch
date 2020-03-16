package com.seint.imagesearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seint.imagesearch.model.ImageModel

class ImageAdapter (private val context: Context) : RecyclerView.Adapter<ImageAdapter.MyViewHolder>(){

    private val layoutInflater = LayoutInflater.from(context)

    private var imgList : List<ImageModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = layoutInflater.inflate(R.layout.image_list_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val imageData = imgList.get(position)
        holder.dataPosition = position
        holder.tvDesp?.text = imageData.title
        holder.tvTitle?.text = imageData.name
    }


    inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvTitle = itemView?.findViewById<TextView?>(R.id.tvTitle)
        var tvDesp = itemView?.findViewById<TextView?>(R.id.tvDesp)
        var imgView = itemView?.findViewById<ImageView?>(R.id.imageView)
        var dataPosition = 0
        init {
            itemView?.setOnClickListener {
                // Snackbar.make(it, mobileUsageList[dataPosition].quarter, Snackbar.LENGTH_LONG).show()
            }
        }
    }
    fun  setImageData( imageData : List<ImageModel>){
        imgList = imageData
        notifyDataSetChanged()
    }
}