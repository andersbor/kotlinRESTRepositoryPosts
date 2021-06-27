package dk.easj.anbo.restrepositorypostsexample

import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import dk.easj.anbo.restrepositorypostsexample.databinding.ActivityMainBinding
import dk.easj.anbo.restrepositorypostsexample.models.Post
import dk.easj.anbo.restrepositorypostsexample.models.PostsViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            showDialog()
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            //    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    // Adapted from https://handyopinion.com/show-alert-dialog-with-an-input-field-edittext-in-android-kotlin/
    private fun showDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("New Post")

        val layout = LinearLayout(this@MainActivity)
        layout.orientation = LinearLayout.VERTICAL

        val titleInputField = EditText(this)
        titleInputField.hint = "Title"
        titleInputField.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(titleInputField)

        val bodyInputField = EditText(this)
        bodyInputField.hint = "Body"
        bodyInputField.inputType = InputType.TYPE_CLASS_TEXT
        layout.addView(bodyInputField)

        builder.setView(layout)

        builder.setPositiveButton("OK") { dialog, which ->
            val title = titleInputField.text.toString().trim()
            val body = bodyInputField.text.toString().trim()
            when {
                title.isEmpty() ->
                    //inputField.error = "No word"
                    Snackbar.make(binding.root, "No title", Snackbar.LENGTH_LONG).show()
                body.isEmpty() -> Snackbar.make(binding.root, "No message", Snackbar.LENGTH_LONG)
                    .show()
                else -> {
                    val post = Post(14593, 123, title, body)
                    val viewModel: PostsViewModel by viewModels()
                    viewModel.add(post)
                }
            }
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }
}