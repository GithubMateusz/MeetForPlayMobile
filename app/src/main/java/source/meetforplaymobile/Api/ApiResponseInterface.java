package source.meetforplaymobile.Api;

import androidx.annotation.NonNull;

public interface ApiResponseInterface {
    void onSuccess(@NonNull String value);

    void onError(@NonNull String throwable);
}
