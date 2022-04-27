package kz.kakainchik.catgenerator.ui

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.slider.Slider
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.plugins.*
import kz.kakainchik.catgenerator.R
import kz.kakainchik.catgenerator.data.CatFilter
import kz.kakainchik.catgenerator.data.DescriptionColor
import kz.kakainchik.catgenerator.util.Option
import kz.kakainchik.catgenerator.vm.CatViewModel

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var giveCatButton: Button

    private var doAddDetails: Boolean = false

    override val viewModel: CatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        giveCatButton = findViewById(R.id.get_cat_bt)

        initializeUI()
        observeError(findViewById(R.id.parent_layout))
        observeProcessingStatus()
    }

    override fun onDataProcessing() {
        giveCatButton.isEnabled = false
    }

    override fun onDataProcessingCompleted() {
        giveCatButton.isEnabled = true
    }

    private fun initializeUI() {
        val optionLayout: LinearLayout = findViewById(R.id.add_text_options_layout)
        val imageView: ImageView = findViewById(R.id.main_cat_image)

        with(findViewById<AutoCompleteTextView>(R.id.choose_tag_text)) {
            viewModel.tags.observe(this@MainActivity, Observer {
                val adapter = ArrayAdapter(
                    this@MainActivity,
                    R.layout.dropdown_text_item,
                    it
                )
                setAdapter(adapter)
            })

            setOnItemClickListener { adapterView, _, pos, _ ->
                viewModel.setTag(adapterView.getItemAtPosition(pos) as String?)
            }

            setOnClickListener {
                if(adapter == null) viewModel.loadTags()
            }
        }

        with(findViewById<AutoCompleteTextView>(R.id.choose_filter_text)) {
            val options = CatFilter.values().map {
                Option(it, getString(it.res))
            }
            setAdapter(ArrayAdapter(
                this@MainActivity,
                R.layout.dropdown_text_item,
                options
            ))

            setOnItemClickListener { adapterView, _, pos, _ ->
                viewModel.setFilter((adapterView.getItemAtPosition(pos) as Option<CatFilter>).value)
            }
        }

        findViewById<CheckBox>(R.id.add_text_chb).setOnCheckedChangeListener { _, isChecked ->
            optionLayout.visibility = if(isChecked) View.VISIBLE else View.GONE
            doAddDetails = isChecked
        }

        findViewById<TextInputEditText>(R.id.description_text).doAfterTextChanged {
            viewModel.setDescription(
                it?.takeUnless { it.isBlank() }.toString()
            )
        }

        with(findViewById<AutoCompleteTextView>(R.id.color_text)) {
            val options = DescriptionColor.values().map {
                Option(it, getString(it.res))
            }
            setAdapter(ArrayAdapter(
                this@MainActivity,
                R.layout.dropdown_text_item,
                options
            ))

            setOnItemClickListener { adapterView, _, pos, _ ->
                viewModel.setColor(
                    (adapterView.getItemAtPosition(pos) as Option<DescriptionColor>).value
                )
            }
        }

        findViewById<Slider>(R.id.font_size_slider).addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                viewModel.setFontSize(slider.value.toInt())
            }
        })

        giveCatButton.setOnClickListener {
            viewModel.fetchCatPhoto(doAddDetails)
        }

        viewModel.catPhoto.observe(this, Observer {
            Glide.with(this)
                .load(it)
                .fitCenter()
                .into(imageView)
        })
    }
}