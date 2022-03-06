package androidx.core.app;


import androidx.fragment.app.Fragment;

import java.io.PrintWriter;
import java.io.StringWriter;

public class BackstackAccessor{
    public static boolean isFragmentOnBackStack(Fragment fragment) {
        return false;
    }

    public static boolean isInBackStack(final Fragment fragment) {
        try {
            return false; //fragment.isInBackStack();
        } catch (IllegalAccessError e) {
            return isInBackStackAndroidX(fragment);
        }
    }

    /**
     * Hacky workaround because Fragment#isInBackStack is inaccessible with AndroidX
     */
    private static boolean isInBackStackAndroidX(final Fragment fragment) {
        final StringWriter writer = new StringWriter();
        fragment.dump("", null, new PrintWriter(writer), null);
        final String dump = writer.toString();
        return !dump.contains("mBackStackNesting=0");
    }
}
