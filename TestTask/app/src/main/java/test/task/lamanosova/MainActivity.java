package test.task.lamanosova;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import test.task.lamanosova.adapter.CategoryAdapter;
import test.task.lamanosova.model.CategoryModel;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewId);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);


        final PackageManager packageManager = getPackageManager();
        //get a list of installed apps.


        List<ApplicationInfo> packages = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);


        List<CategoryModel> categoryModel = getAppsHashAndName(packages, packageManager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModel, this);
        recyclerView.setAdapter(categoryAdapter);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);
        categoryAdapter.notifyDataSetChanged();

    }




    private List<CategoryModel> getAppsHashAndName(List<ApplicationInfo> packages, PackageManager packageManager) {
        ArrayList<CategoryModel> categoryModel = new ArrayList<>();
        for (int i = 0; i < packages.size(); ++i) {
            PackageInfo packageInfo = null;
            try {
                packageInfo = this.getPackageManager().getPackageInfo(
                        packages.get(i).packageName, PackageManager.GET_SIGNATURES);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (packageInfo != null) {
                for (Signature signature : packageInfo.signatures) {

                    String hash2 = getSignatureString(signature, "SHA-256");

                    // String hash = getHash(signature.toByteArray());
                    String app_name = null;
                    try {
                        app_name = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packages.get(i).packageName
                                , PackageManager.GET_META_DATA));
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e(TAG, String.valueOf(e));
                    }
                    categoryModel.add(new CategoryModel("App = " + app_name, "Hash = " + hash2));

                    break;
                }
            }
        }
        return categoryModel;
    }


    public static String getSignatureString(Signature sig, String type) {
        byte[] hexBytes = sig.toByteArray();
        String fingerprint = "error!";
        try {
            MessageDigest digest = MessageDigest.getInstance(type);
            if (digest != null) {
                byte[] digestBytes = digest.digest(hexBytes);
                StringBuilder sb = new StringBuilder();


                sb.append(bytesToHex(digestBytes));
                fingerprint = sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, String.valueOf(e));
        }

        return fingerprint;
    }



    //util method to convert byte array to hex string
    public static String bytesToHex(byte[] bytes) {
        final char[] hexArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for (int j = 0; j < bytes.length; j++) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


}