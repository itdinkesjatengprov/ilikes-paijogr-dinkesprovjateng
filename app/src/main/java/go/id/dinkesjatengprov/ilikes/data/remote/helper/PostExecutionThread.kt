package go.id.dinkesjatengprov.ilikes.data.remote.helper

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler: Scheduler
}