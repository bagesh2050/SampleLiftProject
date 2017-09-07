import kotlin.concurrent.thread

fun main(args: Array<String>) {

    val liftModel = LiftModel(0, Floor.GROUND, Direction.NONE)
    val liftController = LiftController(liftModel)
    thread(true, false, null, "Thread1", -1, { startLiftFirstThread(liftController) })
    thread(true, false, null, "Thread2", -1, { startLiftSecondThread(liftController) })
    liftController.addFloor(Floor.FIRST)
    liftController.addFloor(Floor.FIRST)
    liftController.addFloor(Floor.FIRST)
    liftController.addFloor(Floor.FIRST)
}

fun startLiftFirstThread(liftController: LiftController) {
    Thread.sleep(500)
    liftController.addFloor(Floor.GROUND)
    liftController.addFloor(Floor.SECOND)
    liftController.addFloor(Floor.FOURTH)
}

fun startLiftSecondThread(liftController: LiftController) {
    Thread.sleep(2000)
    liftController.addFloor(Floor.FIRST)
    Thread.sleep(1000)
    liftController.addFloor(Floor.THIRD)
    Thread.sleep(2000)
    liftController.addFloor(Floor.THIRD)
}