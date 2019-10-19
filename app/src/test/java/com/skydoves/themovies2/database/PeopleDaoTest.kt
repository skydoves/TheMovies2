/*
 * Designed and developed by 2019 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.themovies2.database

import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockPerson
import com.skydoves.themovies2.utils.MockTestUtil.Companion.mockPersonList
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [21])
class PeopleDaoTest : LocalDatabase() {

  @Test
  fun insertAndRead() {
    val people = mockPersonList()
    db.peopleDao().insertPeople(people)
    val loadFromDB = db.peopleDao().getPeople(1)[0]
    assertThat(loadFromDB.page, `is`(1))
    assertThat(loadFromDB.id, `is`(123))
  }

  @Test
  fun updateAndRead() {
    val people = mockPersonList()
    val mockPerson = mockPerson()
    db.peopleDao().insertPeople(people)

    val loadFromDB = db.peopleDao().getPerson(mockPerson.id)
    assertThat(loadFromDB.page, `is`(1))

    mockPerson.page = 10
    db.peopleDao().updatePerson(mockPerson)

    val updated = db.peopleDao().getPerson(mockPerson.id)
    assertThat(updated.page, `is`(10))
  }
}
