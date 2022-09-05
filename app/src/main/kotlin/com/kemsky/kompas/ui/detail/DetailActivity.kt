package com.kemsky.kompas.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kemsky.kompas.databinding.ActivityDetailBinding
import com.kemsky.kompas.helper.Resource
import com.kemsky.kompas.helper.loadImage
import com.kemsky.kompas.ui.detail.adapter.RepoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine

// Entry point dependency injection by dagger hilt
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModels()

    private var binding: ActivityDetailBinding? = null

    private val username by lazy {
        intent.extras?.getString("username")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflating UI by viewbinding
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        configureDetails()
    }

    private fun configureDetails() {
        lifecycleScope.launchWhenStarted {
            username?.let { user ->
                viewModel.fetchDetailUser(user).combine(viewModel.fetchListRepo(user)) { detail, repos ->
                    binding?.let { view ->
                        when(detail) {
                            is Resource.Loading -> {
                                view.progressBarDetail.visibility = View.VISIBLE
                                view.groupDetails.visibility = View.GONE
                            }
                            is Resource.Error -> {
                                view.groupDetails.visibility = View.GONE
                            }
                            else -> {
                                view.groupDetails.visibility = View.VISIBLE
                                view.progressBarDetail.visibility = View.GONE

                                view.txtUser.text = detail.data?.name ?: "-"
                                view.txtBio.text = detail.data?.bio?.toString() ?: "No Bio"
                                view.imgAvatar.loadImage(detail.data?.avatarUrl ?: "-")
                                view.txtUsername.text = if(detail.data?.login == null) "-" else ("@" + detail.data.login)

                            }
                        }

                        when(repos) {
                            is Resource.Loading -> {
                                view.progressBarRepos.visibility = View.VISIBLE
                                view.rvRepos.visibility = View.GONE
                            }
                            is Resource.Error -> {
                                view.progressBarRepos.visibility = View.GONE
                                view.rvRepos.visibility = View.GONE
                                Toast.makeText(this@DetailActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                view.progressBarRepos.visibility = View.GONE
                                view.rvRepos.visibility = View.VISIBLE
                                val repoAdapter = RepoAdapter()
                                view.rvRepos.layoutManager = LinearLayoutManager(this@DetailActivity)
                                view.rvRepos.setHasFixedSize(true)
                                view.rvRepos.adapter = repoAdapter
                                repoAdapter.submitList(repos.data)
                            }
                        }

                    }


                }.collect()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Avoiding memory leak in databinding/viewbinding
        binding = null
    }
}