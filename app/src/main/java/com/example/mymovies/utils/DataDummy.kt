package com.example.mymovies.utils

import com.example.mymovies.data.source.local.entity.MovieEntity
import com.example.mymovies.data.source.remote.response.Movie
import com.example.mymovies.data.source.remote.response.TvShow

object DataDummy {
    fun generateDummyMovies(): List<MovieEntity> {
        return listOf(
            MovieEntity(
                "The tale of an extraordinary family, the Madrigals, who live hidden in the mountains of Colombia, in a magical house, in a vibrant town, in a wondrous, charmed place called an Encanto. The magic of the Encanto has blessed every child in the family with a unique gift from super strength to the power to heal—every child except one, Mirabel. But when she discovers that the magic surrounding the Encanto is in danger, Mirabel decides that she, the only ordinary Madrigal, might just be her exceptional family's last hope.",
                "en",
                "2021-11-24",
                6449.416,
                7.8,
                568124,
                "Encanto",
                "/3G1Q5xF40HkUBJXxt2DQgQzKTp5.jpg",
                ),
            MovieEntity(
                "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                "en",
                "2021-12-15",
                7202.317,
                8.4,
                634649,
                "Spider-Man: No Way Home",
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"
            ),
            MovieEntity(
                "When a single mom and her two kids arrive in a small town, they begin to discover their connection to the original Ghostbusters and the secret legacy their grandfather left behind.",
                "en",
                "2021-11-11",
                7179.505,
                7.2,
                425909,
                "Ghostbusters: Afterlife",
                "/sg4xJaufDiQl7caFEskBtQXfD4x.jpg"
            )
        )
    }

    fun generateDummyTvShows(): List<MovieEntity>{
        val tvShows = ArrayList<MovieEntity>()
        tvShows.add(
            MovieEntity(
                "2018-05-02",
                "This Karate Kid sequel series picks up 30 years after the events of the 1984 All Valley Karate Tournament and finds Johnny Lawrence on the hunt for redemption by reopening the infamous Cobra Kai karate dojo. This reignites his old rivalry with the successful Daniel LaRusso, who has been working to maintain the balance in his life without mentor Mr. Miyagi.",
                "en",
                3190.743,
                8.1,
                77169,
                "Cobra Kai",
                "/6POBWybSBDBKjSs1VAQcnQC1qyt.jpg"
            )
        )
        tvShows.add(
            MovieEntity(
                "2021-12-29",
                "Legendary bounty hunter Boba Fett and mercenary Fennec Shand must navigate the galaxy’s underworld when they return to the sands of Tatooine to stake their claim on the territory once ruled by Jabba the Hutt and his crime syndicate.",
                "en",
                2717.243,
                8.3,
                115036,
                "The Book of Boba Fett",
                "/gNbdjDi1HamTCrfvM9JeA94bNi2.jpg"
            )
        )
        tvShows.add(
            MovieEntity(
                "2019-06-16",
                "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
                "en",
                2351.685,
                8.4,
                85552,
                "Euphoria",
                "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg"
            )
        )
        return tvShows
    }

    fun generateRemoteDummyMovies(): List<Movie> {
        val movies = ArrayList<Movie>()
        movies.add(
            Movie(
                "The tale of an extraordinary family, the Madrigals, who live hidden in the mountains of Colombia, in a magical house, in a vibrant town, in a wondrous, charmed place called an Encanto. The magic of the Encanto has blessed every child in the family with a unique gift from super strength to the power to heal—every child except one, Mirabel. But when she discovers that the magic surrounding the Encanto is in danger, Mirabel decides that she, the only ordinary Madrigal, might just be her exceptional family's last hope.",
                "en",
                "2021-11-24",
                6449.416,
                7.8,
                568124,
                "Encanto",
                "/3G1Q5xF40HkUBJXxt2DQgQzKTp5.jpg"
            )
        )
        movies.add(
            Movie(
                "Peter Parker is unmasked and no longer able to separate his normal life from the high-stakes of being a super-hero. When he asks for help from Doctor Strange the stakes become even more dangerous, forcing him to discover what it truly means to be Spider-Man.",
                "en",
                "2021-12-15",
                7202.317,
                8.4,
                634649,
                "Spider-Man: No Way Home",
                "/1g0dhYtq4irTY1GPXvft6k4YLjm.jpg"
            )
        )
        movies.add(
            Movie(
                "When a single mom and her two kids arrive in a small town, they begin to discover their connection to the original Ghostbusters and the secret legacy their grandfather left behind.",
                "en",
                "2021-11-11",
                7179.505,
                7.2,
                425909,
                "Ghostbusters: Afterlife",
                "/sg4xJaufDiQl7caFEskBtQXfD4x.jpg"
            )
        )
        return movies
    }

    fun generateRemoteDummyTvShows(): List<TvShow> {
        val tvShows = ArrayList<TvShow>()
        tvShows.add(
            TvShow(
                "2018-05-02",
                "This Karate Kid sequel series picks up 30 years after the events of the 1984 All Valley Karate Tournament and finds Johnny Lawrence on the hunt for redemption by reopening the infamous Cobra Kai karate dojo. This reignites his old rivalry with the successful Daniel LaRusso, who has been working to maintain the balance in his life without mentor Mr. Miyagi.",
                "en",
                3190.743,
                8.1,
                "Cobra Kai",
                77169,
                "/6POBWybSBDBKjSs1VAQcnQC1qyt.jpg"
            )
        )
        tvShows.add(
            TvShow(
                "2021-12-29",
                "Legendary bounty hunter Boba Fett and mercenary Fennec Shand must navigate the galaxy’s underworld when they return to the sands of Tatooine to stake their claim on the territory once ruled by Jabba the Hutt and his crime syndicate.",
                "en",
                2717.243,
                8.3,
                "The Book of Boba Fett",
                115036,
                "/gNbdjDi1HamTCrfvM9JeA94bNi2.jpg"
            )
        )
        tvShows.add(
            TvShow(
                "2019-06-16",
                "A group of high school students navigate love and friendships in a world of drugs, sex, trauma, and social media.",
                "en",
                2351.685,
                8.4,
                "Euphoria",
                85552,
                "/jtnfNzqZwN4E32FGGxx1YZaBWWf.jpg"
            )
        )
        return tvShows
    }
}
