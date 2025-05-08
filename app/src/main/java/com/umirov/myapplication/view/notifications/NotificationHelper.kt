package com.umirov.myapplication.view.notifications

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.bumptech.glide.request.target.CustomTarget
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import com.bumptech.glide.request.transition.Transition
import com.umirov.myapplication.R
import com.umirov.remote_module.entity.ApiConstants
import com.umirov.myapplication.data.entity.Film
import com.umirov.myapplication.view.MainActivity


object NotificationHelper {
    fun createNotification(context: Context, film: Film) {
        val mIntent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder =
            NotificationCompat.Builder(context!!, NotificationConstants.CHANNEL_ID).apply {
                setSmallIcon(R.drawable.ic_baseline_watch_later_24)
                setContentTitle("Не забудьте посмотреть!")
                setContentText(film.title)
                setContentIntent(pendingIntent)
                setAutoCancel(true)
                priority = NotificationCompat.PRIORITY_DEFAULT
            }
        val notificationManager = NotificationManagerCompat.from(context)

        Glide.with(context)
            //говорим что нужен битмап
            .asBitmap()
            //указываем откуда загружать, это ссылка как на загрузку с API
            .load(ApiConstants.IMAGES_URL + "w500" + film.poster)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                //Этот колбек отработает когда мы успешно получим битмап
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    //Создаем нотификации в стиле big picture
                    builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    //Обновляем нотификацию
                    notificationManager.notify(film.id, builder.build())
                }
            })
        // Отправляем изначальную нотификацию в стандартном исполнении
        notificationManager.notify(film.id, builder.build())

    }
}
