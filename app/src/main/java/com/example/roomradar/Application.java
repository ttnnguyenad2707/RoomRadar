package com.example.roomradar;

import com.cloudinary.Configuration;
import com.cloudinary.android.MediaManager;

public class Application  extends android.app.Application {
    public void onCreate() {
        super.onCreate();

        // Thực hiện cấu hình và khởi tạo toàn cục ở đây

        // Ví dụ: Khởi tạo MediaManager
        MediaManager.init(this, new Configuration.Builder()
                .setCloudName("dtpujfoo8")
                .setApiKey("697855136624351")
                .setApiSecret("gYkgLXmSaCiVhCM40clYpA_dFr8")
                .build());
    }
}
