package com.kakawe.admin.kakawev2;

import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//**
// * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
// */
//public class ApplicationTest extends ApplicationTestCase<Application> {
//    public ApplicationTest() {
//        super(Application.class);
//    }


/**
 * Created by eloy on 20/2/18.
 */

public class ApplicationTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private Button mloginbutton;
    private TextView msinguptext;
    private EditText etext1;
    private EditText etext2;
    private LoginActivity actividad;

    public ApplicationTest() {
        super(LoginActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        actividad = getActivity();
        etext1 = (EditText) actividad.findViewById(R.id.et_login_correo);
        etext2 = (EditText) actividad.findViewById(R.id.et_login_contrasena);
        mloginbutton = (Button) actividad.findViewById(R.id.bt_login_entrar);
        // MainActivity actividad = getActivity();
        // suma = (Button) actividad.findViewById(R.id.button1);

    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private static final String USERNAME = "adri1@mail.com";
    private static final String PASSWORD = "123456";

    public void testLoginSignup() {
        TouchUtils.tapView(this, etext1);
        getInstrumentation().sendStringSync(USERNAME);
//        // now on value2 entry
        TouchUtils.tapView(this, etext2);
        getInstrumentation().sendStringSync(PASSWORD);
        // now on Add button
        TouchUtils.clickView(this, mloginbutton);
//        mloginbutton.requestFocus();
//        mloginbutton.performClick();
        // now on Sign up text

        // get result
//        String mathResult1 = etiqueta.getText().toString();
        // check the result
//        assertTrue("Add result should be 5", mathResult1.equals(ADD_RESULT));
    }


}