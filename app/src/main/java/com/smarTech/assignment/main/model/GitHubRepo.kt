package com.smarTech.assignment.main.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Repos")
class GitHubRepo(
    @PrimaryKey @SerializedName("id") val apiId: String,
    @SerializedName("name") val name: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("private") val isPrivate: Boolean?,
    @SerializedName("description") val description: String?
)

