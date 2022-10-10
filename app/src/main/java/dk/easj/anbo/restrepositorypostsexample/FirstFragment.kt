package dk.easj.anbo.restrepositorypostsexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dk.easj.anbo.restrepositorypostsexample.databinding.FragmentFirstBinding
import dk.easj.anbo.restrepositorypostsexample.models.PostsViewModel

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    private val postsViewModel: PostsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsViewModel.postsLiveData.observe(viewLifecycleOwner) { posts ->
            if (posts != null) {
                binding.textviewFirst.text = posts.toString()

                val adapter = MyAdapter(posts) { position ->
                    Toast.makeText(
                        activity,
                        "Clicked " + postsViewModel.get(position),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.recyclerview.layoutManager = LinearLayoutManager(activity)

                binding.recyclerview.adapter = adapter
            }
        }

        postsViewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            binding.textviewFirst.text = errorMessage
        }

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}