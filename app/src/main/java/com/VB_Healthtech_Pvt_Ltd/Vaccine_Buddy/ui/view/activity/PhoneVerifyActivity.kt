package com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.R
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.databinding.ActivityPhoneVerifyBinding
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.BaseViewModel.ViewModalLogin
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Constants.MyConstants
import com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Repository.PrefrencesHelper
import com.catalyist.helper.ErrorUtil
import com.goodiebag.pinview.Pinview
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.*
import java.util.concurrent.TimeUnit

class PhoneVerifyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneVerifyBinding
    private lateinit var auth: FirebaseAuth
    private var storedverificationId = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var viewModel: ViewModalLogin
    private var getphone: String = ""
    private var getemail: String = ""
    private var getOtp: String = ""
    private var firebaseOpt: String = ""
    private var otp: Pinview? = null
    var mapData: HashMap<String, Any>? = null
    var email_opt = ""
    private var get_forgot_data: String = ""
    private var get_forgot_Otp: String = ""
    private var from = ""
    private var mCountDownTimer: CountDownTimer? = null
    private var mTimerRunning = false
    private val START_TIME_IN_MILLIS: Long = 30000
    private var mTimeLeftInMillis = START_TIME_IN_MILLIS
    private var enterOtp = ""
    var key: String = ""
    var key1: String = ""
    var package_data_transfer: com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneVerifyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        key = PrefrencesHelper.setEmailKey(this)
        /*key1 = PrefrencesHelper.setKey(this)
        println("-----key1$key1")
        if (key1.equals("package")) {
            package_data_transfer = Gson().fromJson(
                intent.getStringExtra("Pkg"),
                com.VB_Healthtech_Pvt_Ltd.Vaccine_Buddy.ui.view.Modal.PackageDetails.AskedPackage::class.java
            )
        }
        println("-----ppppphone$package_data_transfer")*/
        auth = FirebaseAuth.getInstance()
//        auth.firebaseAuthSettings.setAppVerificationDisabledForTesting(true)
        auth.firebaseAuthSettings.forceRecaptchaFlowForTesting(false)
        apiResponse()
        initView()
        lstnr()
        otp = findViewById(R.id.firstPinView1)
        otp?.get(0)?.requestFocus()
        val editTextLast = (otp?.get(5) as EditText)
        editTextLast.doAfterTextChanged {
            if (editTextLast.text.isEmpty()) {
                editTextLast.background = ContextCompat.getDrawable(this, R.drawable.opt_bg)
            } else {
                editTextLast.background =
                    ContextCompat.getDrawable(this, R.drawable.filled_otp_back)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        binding.btn.tv.text = "Next"
        getemail = intent.getStringExtra("email").toString()
        getphone = intent.getStringExtra("mobile").toString()
        getOtp = intent.getStringExtra("otp").toString()
        from = intent.getStringExtra("from").toString()
        println("-----from$from")
        if (from.equals("forgot")) {
            get_forgot_data = intent.getStringExtra("forgot_data").toString()
            getOtp = intent.getStringExtra("forgot_otp").toString()
            println("-----get_forgot_data$get_forgot_data")
            println("-----get_forgot_Otp$get_forgot_Otp")
            if (get_forgot_data.contains("@")) {
                binding.tvphoneNumber.text = "SMS on $get_forgot_data"
                binding.textView229.visibility = View.INVISIBLE
                startTimer()
            } else {
                binding.tvphoneNumber.text = "SMS on $get_forgot_data"
                sendVerificationcode()
            }

        } else if (getphone!!.isNotBlank()) {
            sendVerificationcode()
            binding.tvphoneNumber.text = "SMS on $getphone"
        } else {
            binding.textView229.visibility = View.VISIBLE
            binding.tvphoneNumber.text = "SMS on $getemail"
            startTimer()
        }
        println("email${getemail}")
        println("phone${getphone}")
        println("getotp${getOtp}")

    }

    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                if (p0 != null) {
                    firebaseOpt = p0.smsCode.toString()
                    println("-----$firebaseOpt")
                    try {
                        (otp?.get(0) as TextView).text = firebaseOpt[0].toString()
                        (otp?.get(1) as TextView).text = firebaseOpt[1].toString()
                        (otp?.get(2) as TextView).text = firebaseOpt[2].toString()
                        (otp?.get(3) as TextView).text = firebaseOpt[3].toString()
                        (otp?.get(4) as TextView).text = firebaseOpt[4].toString()
                        (otp?.get(5) as TextView).text = firebaseOpt[5].toString()
                    } catch (e: Exception) {
                        println("---firebseopt exception" + e.message)
                    }

                    println("---firebseopt$$p0")
                    Toast.makeText(this@PhoneVerifyActivity, "Success", Toast.LENGTH_SHORT).show()

                }
            }


            override fun onCodeSent(
                verificationId: String, token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("TAG", "onCodeSent: $verificationId")
                storedverificationId = verificationId
                resendToken = token
            }

            override fun onVerificationFailed(p0: FirebaseException) {
//                Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
                Log.d("TAG", "onVerificationFailed: .................")
                if (getemail.isNullOrEmpty()) {
                    Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
                } /*else if (getOtp == otp.toString()) {
                    //  apiCallingForOtpVerify()
                    apiCallingForSignUp()
                }*/ else {
//                    Toast.makeText(
//                        applicationContext,
//                        "Please Enter Correct Opt!!",
//                        Toast.LENGTH_LONG
//                    ).show()
                    Toast.makeText(applicationContext, p0.message, Toast.LENGTH_LONG).show()
                }

            }
        }

    private fun sendVerificationcode() {
        if (from.equals("forgot")) {
            var option = PhoneAuthOptions.newBuilder(auth).setPhoneNumber("+91" + get_forgot_data)
                .setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(callbacks).build()
            PhoneAuthProvider.verifyPhoneNumber(option)
            startTimer()
        } else {
            var option = PhoneAuthOptions.newBuilder(auth).setPhoneNumber("+91" + getphone)
                .setTimeout(60L, TimeUnit.SECONDS).setActivity(this).setCallbacks(callbacks).build()
            PhoneAuthProvider.verifyPhoneNumber(option)
            startTimer()
        }


    }

    private fun VerifyOtp(otp: String) {
        if (from.equals("forgot")) {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            intent.putExtra("forgot_data", get_forgot_data)
            startActivity(intent)
            finish()
        } else {
            if (storedverificationId.isNotEmpty()) {
                val credential = PhoneAuthProvider.getCredential(storedverificationId, otp)
                signInWithPhoneCredention(credential)
            }

        }
//            } else if (getOtp != null && getOtp.length == 6) {
//                if (getOtp.toInt() == otp.toInt()) {
//                    apiCallingForSignUp()
//                } else {
//                    Toast.makeText(this, "Please Enter Valid OTP!", Toast.LENGTH_SHORT).show()
//                }
//
//            } else {
//                val credential = PhoneAuthProvider.getCredential(storedverificationId, otp)
//                signInWithPhoneCredention(credential)
//            }


    }

    private fun signInWithPhoneCredention(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                //apiCallingForSignUp()
                apiCallingForLogin()
            } else {
                if (getOtp != null && getOtp.length == 6) {
                    if (getOtp.toInt() == enterOtp.toInt()) {
                        //   apiCallingForSignUp()
                        apiCallingForLogin()
                    } else {
                        Toast.makeText(this, "Please Enter Valid OTP!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please Enter Valid OTP!", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun startTimer() {
        mTimerRunning = true
        if (mCountDownTimer != null) {
            mCountDownTimer!!.cancel()
            mCountDownTimer!!.start()
        } else {
            mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    mTimeLeftInMillis = millisUntilFinished
                    updateCountDownText()
                }

                override fun onFinish() {
                    mTimerRunning = false
                    updateCountDownText()
                }
            }.start()
        }
    }

    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        //binding.tvtime.setText(timeLeftFormatted)
        if (mTimerRunning) {
            //  binding.tvtime.visibility= View.VISIBLE
            binding.constraintLayout29.visibility = View.INVISIBLE
            binding.textView229.visibility = View.INVISIBLE
        } else {
            binding.constraintLayout29.visibility = View.VISIBLE
            binding.textView229.visibility = View.VISIBLE
            //binding.tvtime.visibility= View.INVISIBLE
        }
    }

    /*  override fun onComplete(p0: Task<AuthResult>) {
          if (p0.isSuccessful) {
              //apiCallingForCheckUser()
          } else {
              if (p0.exception is FirebaseAuthInvalidCredentialsException) {

                  Toast.makeText(this, "" + p0.exception, Toast.LENGTH_LONG).show()


              }
          }
       }
  */
    private fun apiResponse() {
        viewModel = ViewModelProvider(this).get(ViewModalLogin::class.java)
        viewModel.responseSignup.observe(this) {
            if (it?.code == 200) {
                if (key.equals("package")) {
                    startActivity(
                        Intent(
                            applicationContext, CreatePasswordActivity::class.java
                        ).putExtra("phoneEmail", getemail)
                            .putExtra("phoneNumber", getphone)/*.putExtra(
                                "Pkg",
                                Gson().toJson(package_data_transfer)
                            )*/
                    )
                } else {
                    startActivity(
                        Intent(
                            applicationContext, CreatePasswordActivity::class.java
                        ).putExtra("phoneEmail", getemail).putExtra("phoneNumber", getphone)
                    )
                }


                // Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            } else if (it?.code == 401) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.responseOtpEmail.observe(this) {
            if (it?.code == 200) {
                getOtp = it.user.otp
                println("--otp${getOtp}")
            }
        }
        viewModel.responseOtpVerify.observe(this) {
            if (it?.code == 200) {
                if (from.equals("forgot")) {
                    val intent = Intent(this, ChangePasswordActivity::class.java)
                    intent.putExtra("forgot_data", get_forgot_data)
                    startActivity(intent)
                    finish()
                } else {
                    apiCallingForSignUp()
                }

            } else if (it?.code == 409) {
                PrefrencesHelper.saveFullName(this, "")
                PrefrencesHelper.saveGender(this, "")
                PrefrencesHelper.saveDate(this, "")
                PrefrencesHelper.saveEmail(this, "")
                PrefrencesHelper.saveRefCode(this, "")
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }




        viewModel.responseLogin.observe(this) {
            if (it?.code == 200) {
                PrefrencesHelper.getSaveToken(this, it.token)
                PrefrencesHelper.saveLoginStatus(this, true)
                //PrefrencesHelper.saveRefCode(this, it.activeUser.myRefferral)
                startActivity(MainActivity.getIntent(this))

                finish()
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            } else if (it?.code == 401) {
                Toast.makeText(this, "Please enter Valid Credentials!", Toast.LENGTH_SHORT).show()
            } else if (it?.code == 404) {
                Toast.makeText(
                    this,
                    "Your Credential is not Register with us!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
            }
        }






        viewModel.error.observe(this) {
            ErrorUtil.handlerGeneralError(this, it)

        }
    }


    private fun apiCallingForOtpEmail() {
        val userMap: HashMap<String, Any> = prepareDataforOtpEmail()
        viewModel.onOtpEmail(this, userMap)
    }

    private fun prepareDataforOtpEmail(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kEmail] = getemail
        return hashMap
    }

    private fun apiCallingForOtpVerify() {
        val userMap: HashMap<String, Any> = prepareDataforOtpVerfiy()
        viewModel.onOtpVerify(this, userMap)
    }

    private fun prepareDataforOtpVerfiy(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kEmail] = if (from.equals("forgot")) get_forgot_data else getemail
        hashMap[MyConstants.kotp] = getOtp
        println("-----Haaa$hashMap")
        return hashMap
    }

    private fun apiCallingForSignUp() {
        val userMap: HashMap<String, Any> = prepareDataforSignUp()
        viewModel.onSignup(this, userMap)
    }

    private fun prepareDataforSignUp(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap[MyConstants.kEmail] = getemail
        hashMap[MyConstants.kName] = PrefrencesHelper.getFullName(this)
        hashMap[MyConstants.kGender] = PrefrencesHelper.getGender(this)
        hashMap[MyConstants.kEmail] = PrefrencesHelper.getEmail(this)
        hashMap[MyConstants.kphoneNumber] = PrefrencesHelper.getEmail_Phone(this)
        hashMap[MyConstants.kDob] = PrefrencesHelper.getDate(this)
        hashMap[MyConstants.kreferralCode] = PrefrencesHelper.getRefCode(this)
        return hashMap
    }


    private fun apiCallingForLogin() {
        val userMap: HashMap<String, Any> = prepareData()
        viewModel.userLogin(this, userMap)
    }

    private fun prepareData(): HashMap<String, Any> {
        val hashMap: HashMap<String, Any> = HashMap()
        val deviceToken = PrefrencesHelper.setSaveDeviceID(this)
        println("devide----${deviceToken}")
        hashMap[MyConstants.kemailPhone] = getphone
        hashMap[MyConstants.kPassword] = ""
        hashMap[MyConstants.kDeviceToken] = deviceToken
        hashMap[MyConstants.kdeviceType] = "android"
        return hashMap
    }


    private fun lstnr() {

        binding.textView229.setOnClickListener {
            if (from.equals("forgot")) {
                finish()
            } else if (from.equals("newOTPActivity")) {
                finish()
            }/* else if (key1.equals("package")) {
                PrefrencesHelper.getKey(this, "package")
                startActivity(
                    Intent(this, AccountCreateActivity::class.java)
                        .putExtra(
                        "Pkg",
                        Gson().toJson(package_data_transfer)
                    )
                )
            }*/ else {
                startActivity(
                    Intent(this, AccountCreateActivity::class.java).putExtra(
                        "back_email", getemail
                    ).putExtra("back_phone", getphone)
                        .putExtra("back_name", PrefrencesHelper.getFullName(this))
//                    .putExtra("back_referral", PrefrencesHelper.getRefCode(this))
                        .putExtra("back_dob", PrefrencesHelper.getDate(this))
                        .putExtra("back_gender", PrefrencesHelper.getGender(this))
                )
            }


        }
        binding.tvResend.setOnClickListener {

            if (from.equals("forgot")) {
                if (get_forgot_data.contains("@")) {
                    binding.tvphoneNumber.text = get_forgot_data
                    binding.textView229.visibility = View.INVISIBLE
                    binding.constraintLayout29.visibility = View.INVISIBLE
                    binding.textView229.visibility = View.INVISIBLE
                } else {
                    binding.tvphoneNumber.text = "SMS on $get_forgot_data"
                    sendVerificationcode()
                    binding.constraintLayout29.visibility = View.INVISIBLE
                    binding.textView229.visibility = View.INVISIBLE
                    Toast.makeText(this, "OTP Resend Successfully!", Toast.LENGTH_SHORT).show()
                }

            } else if (getphone!!.isNotBlank()) {
                sendVerificationcode()
                binding.tvphoneNumber.text = "SMS on $getphone"
                binding.constraintLayout29.visibility = View.INVISIBLE
                binding.textView229.visibility = View.INVISIBLE
                Toast.makeText(this, "OTP Resend Successfully!", Toast.LENGTH_SHORT).show()
            } else if (getemail.contains("@")) {
                apiCallingForOtpEmail()
            }
        }
        otp?.setPinViewEventListener { pinview, fromUser ->
//            mapData?.put(MyConstants.kFotp, pinview.value)
            email_opt = otp?.value.toString()
            println("--val$email_opt")
        }
        binding.btn.tv.setOnClickListener {
            if (!isValideField()) {
                return@setOnClickListener
            } else if (key.equals("emailKey")) {
                Log.d("CHECKING_FOR_OTP", "$getOtp: ${otp?.value}")
                if (getOtp == otp?.value) {
                    apiCallingForOtpVerify()
                } else {
                    Toast.makeText(this, "Invalid Otp!!", Toast.LENGTH_SHORT).show()
                }

            } else if (from.equals("forgot")) {
                apiCallingForOtpVerify()

            } else {
                VerifyOtp(otp?.value.toString())
                enterOtp = otp?.value.toString()
            }

        }
    }

    private fun isValideField(): Boolean {
        var isValid = true
        if (otp?.value!!.length < 6) {
            Toast.makeText(this, "Please enter OTP", Toast.LENGTH_SHORT).show()
            isValid = false
            println("----oyyyyy$otp")
        }
        return isValid

    }

}
