package be.vergauwen.simon.androidtestingexample.core.rx

import rx.Observable

interface Transformers {
  fun <T> applyComputationSchedulers(): Observable.Transformer<T, T>
  fun <T> applyIOSchedulers(): Observable.Transformer<T, T>
}