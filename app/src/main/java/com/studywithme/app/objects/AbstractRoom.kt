package com.studywithme.app.objects

abstract class AbstractRoom: AbstractObject() {
	abstract fun getId(): String
	abstract fun getTitle(): String
	abstract fun getTheme(): String
	abstract fun getDescription(): String
	abstract fun isPrivate(): Boolean
	abstract fun getPhotoUri(): String
}