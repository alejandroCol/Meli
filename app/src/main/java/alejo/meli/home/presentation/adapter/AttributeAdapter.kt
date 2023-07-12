package alejo.meli.home.presentation.adapter

import alejo.meli.R
import alejo.meli.databinding.AttributeItemBinding
import alejo.meli.databinding.DetailProductItemBinding
import alejo.meli.home.domain.model.Attribute
import alejo.meli.home.domain.model.Product
import alejo.meli.utils.hide
import alejo.meli.utils.loadImage
import alejo.meli.utils.show
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AttributeAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var attributes: List<Attribute?> = emptyList()
    var detailProduct: Product? = null
    private val TYPE_FIRST_ITEM = 0
    private val TYPE_NORMAL_ITEM = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_FIRST_ITEM -> {
                DetailViewHolder(
                    DetailProductItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            TYPE_NORMAL_ITEM -> {
                AttributeViewHolder(
                    AttributeItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AttributeViewHolder) {
            holder.bind(attributes[position - 1]!!)
        } else if (holder is DetailViewHolder) {
            holder.bind(detailProduct)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            TYPE_FIRST_ITEM
        } else {
            TYPE_NORMAL_ITEM
        }
    }

    override fun getItemCount(): Int = attributes.size

    inner class AttributeViewHolder(private val binding: AttributeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(attribute: Attribute) {
            binding.apply {
                attribute.also {
                    tvName.text = it.name
                    tvValue.text = it.value
                }
            }
        }
    }

    inner class DetailViewHolder(private val binding: DetailProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product?) {
            binding.apply {
                tvTitle.text = product?.title
                ivProduct.loadImage(product?.thumbnail)
                tvShipping.show(product?.shipping?.freeShipping == true)
                tvPrice.text = "$" + product?.price
                product?.officialStoreName?.let {
                    tvStore.text = it
                    tvLabelStore.show()
                }
                product?.originalPrice?.let {
                    tvOldPrice.text = "$" + it
                    tvOldPrice.show()
                    tvOldPrice.paintFlags = tvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                when (product?.condition) {
                    Product.Condition.NEW.code ->
                        tvCondition.text =
                            itemView.context.getString(R.string.condition_new)
                    Product.Condition.USED.code ->
                        tvCondition.text =
                            itemView.context.getString(R.string.condition_used)
                    else -> tvCondition.hide()
                }
            }
        }
    }
}
