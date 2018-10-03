package dev.com.sfilizzola.gygchallenge.utils

import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.function.Consumer

class RxBus {
    companion object {
        private val sSubject = PublishSubject.create<Any>()

        fun subscribe(@NonNull action:Consumer<Any>):Disposable{
            return sSubject.subscribe{action}
        }

        fun publish(@NonNull message:Any) {
            sSubject.onNext(message)
        }
    }
}