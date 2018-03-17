package com.games.memorygame.injection.module

import com.games.memorygame.model.game.BoardConfiguration
import com.games.memorygame.network.ImageDownloader
import com.games.memorygame.network.MemoryGameService
import com.games.memorygame.persistence.ScoreDataSource
import com.games.memorygame.ui.board.BoardPresenter
import com.games.memorygame.ui.score.ScorePresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun provideBoardPresenter(boardConfiguration: BoardConfiguration,
                              service: MemoryGameService,
                              scoreLocalDataSource: ScoreDataSource,
                              imageDownloader: ImageDownloader): BoardPresenter =
            BoardPresenter(boardConfiguration, service, scoreLocalDataSource, imageDownloader)

    @Provides
    fun provideScorePresenter(scoreLocalDataSource: ScoreDataSource) =
            ScorePresenter(scoreLocalDataSource)
}