package nl.biopet.tools.template

import java.io.File

object Documentation {
  def main(args: Array[String]): Unit = {
    val docsDir: File = new File(args(0))
    val readme: File = new File(args(1))
    ToolTemplate.generateDocumentation(docsDir)
    ToolTemplate.generateReadme(readme)
  }
}
