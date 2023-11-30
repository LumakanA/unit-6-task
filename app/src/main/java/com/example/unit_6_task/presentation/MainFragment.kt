package com.example.unit_6_task.presentation

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unit_6_task.R
import com.example.unit_6_task.databinding.FragmentMainBinding
import com.example.unit_6_task.domain.model.Product
import com.example.unit_6_task.domain.model.ProductAdapter
import com.example.unit_6_task.presentation.MainFragment.SampleProductData.products

class MainFragment : Fragment() {
    private val productsAdapter by lazy { ProductAdapter() }
    private lateinit var binding: FragmentMainBinding
    private lateinit var progressContainer: ProgressContainer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        progressContainer = binding.progressContainer

        val loginSuccess = arguments?.getBoolean("loginSuccess") ?: false
        if (loginSuccess) {
            progressContainer.state = ProgressContainer.State.Success
        } else {
            progressContainer.state =
                ProgressContainer.State.Notice("Ну всё, сегодня ты без товаров. Попробуй кнопку нажать что ли, может что и выйдет.")
        }

        val recyclerView = binding.recyclerView
        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
            ResourcesCompat.getDrawable(resources, R.drawable.divider, null)?.let {
                dividerItemDecoration.setDrawable(it)
            }
            addItemDecoration(dividerItemDecoration)

            val verticalMargin = resources.getDimensionPixelSize(R.dimen.item_vertical_margin)
            val topBottomItemDecoration = object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val position = parent.getChildAdapterPosition(view)
                    val itemCount = state.itemCount
                    if (position == 0) {
                        outRect.top = verticalMargin
                    }
                    if (position == itemCount - 1) {
                        outRect.bottom = verticalMargin
                    }
                }
            }
            addItemDecoration(topBottomItemDecoration)

            adapter = productsAdapter
        }
        this.loadProducts(progressContainer.state)


        progressContainer.onRetryButtonClick = {
            retryLoadingData()
        }

        return binding.root
    }

    private fun retryLoadingData() {
        progressContainer.state = ProgressContainer.State.Loading
        loadProducts(progressContainer.state)
    }

    private fun loadProducts(state: ProgressContainer.State) {
        if (state is ProgressContainer.State.Success) {
            productsAdapter.submitList(emptyList())
            binding.recyclerView.isVisible = true
            productsAdapter.submitList(products)
        }
    }

    object SampleProductData {
        val originalProducts = listOf(
            Product(
                "1",
                "Толстовка серая",
                "Толстовка",
                "https://cdn.laredoute.com/products/1200by1200/8/6/c/86c1c8a920dd76652ab4b033e524038b.jpg"
            ),
            Product(
                "2",
                "Бейсболка 9FORTY",
                "Бейсболка",
                "https://a.allegroimg.com/original/11e384/7a23c8ce4e318a80f6a29ee27425/Czapka-z-daszkiem-NEW-ERA-NY-Yankees-Diamond-ERA"
            ),
            Product(
                "3",
                "Бейсболка 9FIFTY",
                "Бейсболка",
                "https://www.velosaratov.ru/upload/iblock/9ed/9edcfa39499551f75702920c885d5f12.jpg"
            ),
            Product(
                "4",
                "Футболка серая",
                "Футболка",
                "https://avatars.mds.yandex.net/get-mpic/3694390/img_id202847481641252720.jpeg/orig"
            ),
            Product(
                "5",
                "Шапка серая",
                "Шапка",
                "https://gitwell.ru/assets/images/products/8848/547.10-3-1000x1000.jpg"
            ),
            Product(
                "6",
                "Беговые кроссовки",
                "Кроссовки",
                "https://i.ebayimg.com/images/g/9vQAAOSw411ja7Ou/s-l1600.jpg"
            ),
            Product(
                "7",
                "Джерси белая",
                "Джерси",
                "https://cdn1.ozone.ru/s3/multimedia-7/6674504587.jpg"
            ),
            Product(
                "8",
                "Шорты чёрные",
                "Шорты",
                "https://diamondelectric.ru/images/996/995978/shorti_bonprix_1.jpg"
            )
        )

        val products: List<Product> by lazy {
            originalProducts.repeat(4).shuffled()
        }

        private fun <T> List<T>.repeat(n: Int): List<T> {
            return flatMap { item -> List(n) { item } }
        }
    }

}


