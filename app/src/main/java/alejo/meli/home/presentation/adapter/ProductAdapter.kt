package alejo.meli.home.presentation.adapter

import alejo.meli.databinding.ProductItemBinding
import alejo.meli.home.domain.model.Product
import alejo.meli.home.presentation.listener.ProductListener
import alejo.meli.utils.loadImage
import alejo.meli.utils.setSafeOnClickListener
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ProductAdapter(val listener: ProductListener) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var products: List<Product> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(products[position])

    override fun getItemCount(): Int = products.size

    inner class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                product.also {
                    tvTitle.text = it.title
                    tvPrice.text = "$"+it.price
                    ivProduct.loadImage(it.thumbnail)
                }
            }
            itemView.setSafeOnClickListener { listener.onProductClicked(product) }
        }
    }
}
