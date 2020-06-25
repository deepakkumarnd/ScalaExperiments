class Folder[T](files: Seq[T]) {
  def apply(n: Int): T = files(n)
  def update(n: Int, value: T): Folder[T] = new Folder(files.updated(n, value))
}

val folder = new Folder(Seq("alpha", "beta", "gamma"))

// get second file
folder(2)
val newFolder = folder(2) = "delta"
newFolder(2)
