package com.example.myapplication

import android.graphics.Bitmap
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myapplication.data.CommentDao
import com.example.myapplication.data.MainDatabase
import com.example.myapplication.data.PostDao
import com.example.myapplication.model.Comment
import com.example.myapplication.model.Post
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException
import java.util.*


@RunWith(RobolectricTestRunner::class)
class AddPostTest {

    private lateinit var postDao: PostDao
    private lateinit var db: MainDatabase
    private lateinit var commentDao: CommentDao


    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, MainDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        postDao = db.postDao
        commentDao = db.commentDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    //POST

    @Test
    @Throws(IOException::class)
    fun testAdd() {
        var bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
        var post = Post(0, Date(),"jordy","jordy@hotmail.com", bitmap,"TestText","http://www.google.be",false,false,"name",false)
        postDao.addPost(post)
        assertEquals(post.id , 0)
    }

    @Test
    @Throws(Exception::class)
    fun addTwoPosts() {
        var bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true)
        val post1 = Post(1, Date(),"jordy","jordy@hotmail.com", bitmap,"TestText","http://www.google.be",false,false,"name",false)
        postDao.addPost(post1)
        val post2 = Post(2, Date(),"jordy","jordy@hotmail.com", bitmap,"TestText","http://www.google.be",false,false,"name",false)
        postDao.addPost(post2)
        assertEquals(2,postDao.getCount())
    }

    @Test
    fun deletePost() {
        var bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true)
        val post1 = Post(1, Date(),"jordy","jordy@hotmail.com", bitmap,"TestText","http://www.google.be",false,false,"name",false)
        postDao.addPost(post1)
        postDao.deletePost(post1)
        assertEquals(0,postDao.getCount())
    }

    @Test
    fun updatePost() {
        var bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888).copy(Bitmap.Config.ARGB_8888, true)
        val post1 = Post(1, Date(),"jordy","jordy@hotmail.com", bitmap,"TestText","http://www.google.be",false,false,"name",false)
        postDao.addPost(post1)
        val post2 = Post(1, Date(),"jordyyy","jordy@hotmail.com", bitmap,"TestText","http://www.google.be",false,false,"name",false)
        postDao.updatePost(post2)
        postDao.readPostWithId(0).value?.let { assertEquals(it.user,"jordyyy") }

    }

    //COMMENT


    @Test
    @Throws(Exception::class)
    fun addComment() {
        val comment = Comment(0,0,"Test Text", Date(),false,"user",0,"username")
        commentDao.addComment(comment)
        assertEquals(1,commentDao.getCount())
    }

    @Test
    @Throws(Exception::class)
    fun addTwoComments() {
        val comment = Comment(1,0,"Test Text", Date(),false,"user",0,"username")
        commentDao.addComment(comment)
        val comment2 = Comment(2,0,"Test Text", Date(),false,"user",0,"username")

        commentDao.addComment(comment)
        commentDao.addComment(comment2)

        assertEquals(2,commentDao.getCount())
    }

    @Test
    fun updateComment() {

        val comment = Comment(0,0,"Test Text", Date(),false,"user",0,"username")
        commentDao.addComment(comment)
        val comment2 = Comment(0,1,"Test Text", Date(),false,"user",0,"username")
        commentDao.updateComment(comment2)

        commentDao.getCommentById(0).value?.let { assertEquals(it.postId,1) }
    }

    @Test
    fun deleteReaction() {
        val comment = Comment(1,0,"Test Text", Date(),false,"user",0,"username")
        commentDao.addComment(comment)
        commentDao.deleteComment(1)
        assertEquals(0,commentDao.getCount())

    }



}