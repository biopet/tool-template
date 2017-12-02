package nl.biopet.tools.template

import java.io.File

object Documentation {
  def main(args: Array[String]): Unit = {
    val docsDir: File = new File(args(0))
    val version: String = args(1)
    val redirect: Boolean = args(2).toBoolean
    ToolTemplate.generateDocumentation(
      outputDirectory = docsDir,
      version = version,
      redirect = redirect
    )
  }
}
