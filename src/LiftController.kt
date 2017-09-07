enum class Floor {GROUND, FIRST, SECOND, THIRD, FOURTH, FIFTH }
enum class Direction {UP, DOWN, NONE }

class LiftController(liftModel: LiftModel) {
    private val mUpLiftFloorsList: MutableList<Floor> = mutableListOf<Floor>()
    private val mDownLiftFloorsList: MutableList<Floor> = mutableListOf<Floor>()
    private var mLiftModel: LiftModel = liftModel

    fun startLift() {
        mLiftModel.currentDirection = getLiftDirection()

        if (mLiftModel.currentDirection == Direction.UP) {
            moveLiftUp()
        } else if (mLiftModel.currentDirection == Direction.DOWN) {
            moveLiftDown()
        }
    }

    fun addFloor(newFloor: Floor) {
        //println("Adding Floor:$newFloor Current Ordinal ${mLiftModel.currentFloor.ordinal}, new Ordinal ${newFloor.ordinal}")

        if (mLiftModel.currentFloor.ordinal > newFloor.ordinal) {
            if (mDownLiftFloorsList.filter { it == newFloor }.size <= 0) {
                mDownLiftFloorsList.add(newFloor)
                mDownLiftFloorsList.sort()
                mDownLiftFloorsList.reverse()
            }
            //println("Adding to Down List size=${mDownLiftFloorsList.size} and Floor $newFloor")
        } else if (mLiftModel.currentFloor.ordinal < newFloor.ordinal) {
            if (mUpLiftFloorsList.filter { it == newFloor }.size <= 0) {
                mUpLiftFloorsList.add(newFloor)
                mUpLiftFloorsList.sort()
            }
            //println("Adding to Up List size=${mUpLiftFloorsList.size} and Floor $newFloor")
        }

        if (mLiftModel.currentDirection == Direction.NONE) {
            startLift()
        }
    }

    private fun removeVisitingFloor() {
        when (mLiftModel.currentDirection) {
            Direction.UP -> mUpLiftFloorsList.remove(mLiftModel.currentFloor)
            Direction.DOWN -> mDownLiftFloorsList.remove(mLiftModel.currentFloor)
            else -> {
                //print("Do nothing")
            }
        }
    }

    private fun moveLiftUp() {
        do {
            val currentFloor: Floor = mUpLiftFloorsList.get(0)
            mLiftModel.currentFloor = currentFloor
            removeVisitingFloor()
            println("Lift Going Up to Floor: $currentFloor")
            Thread.sleep(1000); // The time in which lift moves up
        } while (mUpLiftFloorsList.size > 0)

        if (mDownLiftFloorsList.size > 0) {
            mLiftModel.currentDirection = Direction.DOWN
            moveLiftDown()
        } else {
            mLiftModel.currentDirection = Direction.NONE
        }
    }

    private fun moveLiftDown() {
        do {
            val currentFloor = mDownLiftFloorsList.get(0)
            mLiftModel.currentFloor = currentFloor
            removeVisitingFloor()
            println("Lift Going Down to Floor: $currentFloor")
            Thread.sleep(1000) // The time in which lift moves up
        } while (mDownLiftFloorsList.size > 0)

        if (mUpLiftFloorsList.size > 0) {
            mLiftModel.currentDirection = Direction.UP
            moveLiftUp()
        } else {
            mLiftModel.currentDirection = Direction.NONE
        }
    }

    private fun getLiftDirection(): Direction {
        if (mUpLiftFloorsList.size > 0) return Direction.UP
        else if (mDownLiftFloorsList.size > 0) return Direction.DOWN
        else return Direction.NONE
    }
}