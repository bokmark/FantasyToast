package cn.bokmark.fantasytoast

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.bokmark.fantasy_toast.FantasyToast
import kotlinx.android.synthetic.main.activity_sample.*

class SampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        FantasyToast.getInstance().init(this, null)

        btn_default.setOnClickListener { FantasyToast.getInstance().show("btn_default") }
        btn_success.setOnClickListener { FantasyToast.getInstance().success("btn_success") }
        btn_fail.setOnClickListener { FantasyToast.getInstance().fail("btn_fail") }
        btn_error.setOnClickListener { FantasyToast.getInstance().error("btn_error") }
        btn_info.setOnClickListener { FantasyToast.getInstance().info("btn_info") }
        //btn_many.setOnClickListener { FantasyToast.getInstance().many("btn_many") }

    }

    override fun onDestroy() {
        super.onDestroy()
        FantasyToast.getInstance().destroy()
    }
}
