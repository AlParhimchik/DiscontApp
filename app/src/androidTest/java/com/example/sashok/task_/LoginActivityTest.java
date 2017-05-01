package com.example.sashok.task_;

import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.sashok.task_.Answer.Category;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sashok on 1.5.17.
 */

@MediumTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureRececlerViewIsPresent() throws Exception {
        final MainActivity activity = rule.getActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<Category> categoryList = new ArrayList<>();
                Category category = new Category();
                category.setName("Лосось");
                category.setIconUrl("http://vremena-goda.su/photo/1-0/157_image_154.jpg");
                categoryList.add(category);

                category = new Category();
                category.setName("Мясо");
                category.setIconUrl("http://api.androidhive.info/images/glide/small/deadpool.jpg");
                categoryList.add(category);

                activity.onResponseSuccess(categoryList);
                Assert.assertNotNull(activity.adapter);
                Assert.assertEquals(activity.adapter.getItemCount(), 2);
            }
        });
    }


}