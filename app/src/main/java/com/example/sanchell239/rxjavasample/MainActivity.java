package com.example.sanchell239.rxjavasample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<LoadingWithState> stringThreadOne = Observable.range(1, 20)
                .map(x -> "url_" + x)
                .observeOn(Schedulers.newThread())
                .concatMap(x -> createLoading(x));


        Observable<LoadingWithState> stringThreadTwo = Observable.range(1, 20)
                .map(x -> "url_" + x)
                .observeOn(Schedulers.newThread())
                .concatMap(x -> createLoading(x));

        Observable.zip(
                stringThreadOne,
                stringThreadTwo,
                (s1, s2) -> new Combination(s1, s2).getResult())
                .subscribe(c -> Log.d("rx", c));
    }

    public class Combination {
        private LoadingWithState first = null;
        private LoadingWithState second = null;

        public Combination(LoadingWithState f, LoadingWithState s) {
            first = f;
            second = s;
        }

        public String getResult() {
            if (first.isSuccess() && second.isSuccess()) {
                return "загружено: " + first.getResult() + " " + second.getResult();
            }

            if (first.isError() || second.isError()) {
                return "ошибка:    " + first.getResult() + " " + second.getResult();
            }

            return "загрузка";
        }
    }

    public Observable<LoadingWithState> createLoading(String url) {
        return Observable.create(o -> {
            o.onNext(new LoadingWithState());

            Observable<String> result = Observable.just(url)
                    .map((String s) -> new LongOp(s))
                    .map(c -> c.getResult());

            result.subscribe(x -> {
                        o.onNext(new LoadingWithState(State.Success, x));
                        o.onComplete();
                    },
                    error -> {
                        o.onNext(new LoadingWithState(State.Error, error.getMessage()));
                        o.onComplete();
                    });
        });
    }



    
}
