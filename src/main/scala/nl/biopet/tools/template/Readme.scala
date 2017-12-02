package nl.biopet.tools.template

import java.io.File

object Readme {
  def main(args: Array[String]): Unit = {
    val readme: File = new File(args(0))
    ToolTemplate.generateReadme(readme)
  }
}
