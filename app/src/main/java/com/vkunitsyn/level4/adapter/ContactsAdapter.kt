package com.vkunitsyn.level4.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.vkunitsyn.level4.R
import com.vkunitsyn.level4.databinding.ContactModelLayoutBinding
import com.vkunitsyn.level4.model.Contact
import com.vkunitsyn.level4.ui.RecyclerViewInterface
import com.vkunitsyn.level4.utils.addPictureGlide


class ContactsAdapter(listener: RecyclerViewInterface) :
    RecyclerView.Adapter<ContactsAdapter.MyViewHolder>(),
    RecyclerViewInterface {


    private lateinit var myRecyclerView: RecyclerView
    var onTrashBinClick: ((Int) -> (Unit))? = null
    private var contactsList = listOf<Contact>()
    private var itemClickListener: RecyclerViewInterface = listener


    inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var binding = ContactModelLayoutBinding.bind(item)
        fun bind(contact: Contact) = binding.apply {
            tvModelUserName.text = contact.name
            tvUserModelCareer.text = contact.career
            ivModelProfilePicture.addPictureGlide(contact.picture)
            ivModelProfilePicture.transitionName = contact.id.toString()
            btnDelete.setOnClickListener {
                /*if I use "adapterPosition" instead of "layoutPosition"
                I get the position shift after a contact deletion*/
                onTrashBinClick?.invoke(layoutPosition)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        myRecyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_model_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactsList[position])
        holder.itemView.setOnClickListener {
            //if I use "position" instead of "layoutPosition"
            //I get the position shift after a contact deletion
            itemClickListener.onItemClick(
                holder.binding.ivModelProfilePicture,
                holder.layoutPosition
            )
        }
    }

    override fun getItemCount(): Int = contactsList.size


    fun refresh(contacts: List<Contact>) {
        contactsList = contacts
    }


    override fun onItemClick(imageView: ImageView, position: Int) {
    }
}