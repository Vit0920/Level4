package com.vkunitsyn.level4.ui

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.vkunitsyn.level4.R
import com.vkunitsyn.level4.databinding.FragmentAddContactBinding
import com.vkunitsyn.level4.model.Contact
import com.vkunitsyn.level4.utils.Constants
import com.vkunitsyn.level4.utils.addPictureGlide
import java.io.File
import java.util.UUID


class AddContactFragment : DialogFragment() {

    private lateinit var binding: FragmentAddContactBinding
    private lateinit var getPhoto: ActivityResultLauncher<Uri>
    private lateinit var chooseImage: ActivityResultLauncher<String>
    private var imageFileUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setWindowAnimations(R.style.dialog_animation_fade)
        binding = FragmentAddContactBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setWindowAnimations(
            R.style.dialog_animation_fade
        )
        binding.ivNewContactPicture.addPictureGlide(R.drawable.default_profile_picture)
        processSaveButtonClick()
        processBackArrowClick()
        processProfilePictureClick()

        getPhoto = registerForActivityResult(ActivityResultContracts.TakePicture()) { result ->
            if (result) {
                binding.ivNewContactPicture.addPictureGlide(imageFileUri!!)
            } else {
                imageFileUri = null
            }
        }

        chooseImage = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            if (result != null) {
                binding.ivNewContactPicture.addPictureGlide(result)
                imageFileUri = result
            }
        }
        processAddPictureClick()
    }

    private fun processProfilePictureClick() {
        binding.ivNewContactPicture.setOnClickListener {
            chooseImage.launch("image/*")
        }
    }


    private fun processAddPictureClick() {

        binding.ibAddPicture.setOnClickListener {
            val image = createImageFile()
            imageFileUri = getUriForFile(requireContext(), Constants.AUTHORITIES, image)
            getPhoto.launch(imageFileUri)
        }
    }

    private fun createImageFile(): File {
        val imagePath = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            Constants.NEW_CONTACT_PROFILE_PICTURE_FILE_NAME, Constants.IMAGE_FORMAT, imagePath
        )
    }

    private fun processBackArrowClick() {
        binding.ibArrowBackAddContact.setOnClickListener {
            dismiss()
        }
    }

    private fun processSaveButtonClick() {

        binding.mbSaveAddContact.setOnClickListener {
            val result: Contact = createNewContact()
            setFragmentResult(
                Constants.ADD_CONTACT_FRAGMENT_RESULT, bundleOf(Constants.ADDED_CONTACT to result)
            )
            dismiss()
        }
    }

    private fun createNewContact(): Contact {
        val newContact = Contact()
        with(newContact) {
            picture = if (imageFileUri != null) {
                imageFileUri.toString()
            } else {
                val drawableId = R.drawable.default_profile_picture
                Constants.RESOURCES_PATH + drawableId
            }
            with(binding) {
                id = UUID.randomUUID().toString()
                name = tietUserNameAddContact.text.toString()
                career = tietCareerAddContact.text.toString()
                phone = tietPhoneAddContact.text.toString()
                address = tietAddressAddContact.text.toString()
                birthday = tietBirthdayAddContact.text.toString()
            }
        }
        return newContact
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }
}


