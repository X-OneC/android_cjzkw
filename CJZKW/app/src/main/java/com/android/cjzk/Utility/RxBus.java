//package com.android.cjzk.Utility;
//
//import android.os.Bundle;
//
//import rx.Observable;
//import rx.subjects.PublishSubject;
//import rx.subjects.SerializedSubject;
//import rx.subjects.Subject;
//
///**
// * Created by XOne on 2016/8/15.
// */
//public class RxBus {
//
//    private static volatile RxBus instance;
//    private final Subject<Object, Object> BUS;
//
//    private RxBus() {
//        BUS = new SerializedSubject<>(PublishSubject.create());
//    }
//
//    public static RxBus getInstance() {
//        if (instance == null) {
//            synchronized (RxBus.class) {
//                if (instance == null) {
//                    instance = new RxBus();
//                }
//            }
//        }
//        return instance;
//    }
//
//    public void send(Bundle o) {
//        BUS.onNext(o);
//    }
//
//    public Observable<Object> toObserverable() {
//        return BUS;
//    }
//
//
//
//}
