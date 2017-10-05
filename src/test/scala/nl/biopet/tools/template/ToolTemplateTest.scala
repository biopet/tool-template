package nl.biopet.tools.template

import nl.biopet.test.BiopetTest
import org.testng.annotations.Test

class ToolTemplateTest extends BiopetTest {
  @Test
  def testMain(): Unit = {
    ToolTemplate.main(Array("-i", "inputFile"))
  }

  @Test
  def testNoArgs(): Unit = {
    intercept[IllegalArgumentException] {
      ToolTemplate.main(Array())
    }
  }
}
