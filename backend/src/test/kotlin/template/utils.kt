package template

import java.util.concurrent.ThreadLocalRandom

fun randomPort(): Int = ThreadLocalRandom.current().nextInt(20000, 50000)
