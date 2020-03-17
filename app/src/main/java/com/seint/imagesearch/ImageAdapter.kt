package com.seint.imagesearch

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seint.imagesearch.model.ImageModel
import com.squareup.picasso.Picasso

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
        Picasso.get().load(imageData.thumbnail).error(R.drawable.error).into(holder.imgView);

    }


    inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvTitle = itemView?.findViewById<TextView?>(R.id.tvTitle)
        var tvDesp = itemView?.findViewById<TextView?>(R.id.tvDesp)
        var imgView = itemView?.findViewById<ImageView?>(R.id.imageView)
        var dataPosition = 0
        init {
            itemView?.setOnClickListener {

                val intent = Intent(context,ImageViewActivity::class.java)
                intent.putExtra("ImageUrl",imgList.get(dataPosition).imageUrl)
                context.startActivity(intent)
            }
        }
    }
    fun  setImageData( imageData : List<ImageModel>){
        imgList = imageData
        notifyDataSetChanged()
    }
}