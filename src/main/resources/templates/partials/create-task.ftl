<div>
    <h3>Add task</h3>
    <div class="alert alert-success" ng-if="success === true">Task created successfully.</div>
    <form name="taskCreation">
        <task-form task="task"></task-form>
        <button type="button" class="btn btn-primary" ng-disabled="taskCreation.$invalid" ng-click="createTask()">
            <i class="fa fa-plus mr-2"></i> Create task
        </button>
    </form>
</div>