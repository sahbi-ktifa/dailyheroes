<div class="task-form">
    <div class="form-group">
        <label for="name" class="col-sm-3 control-label">Name *</label>
        <input type="text" id="name" class="form-control" ng-model="task.name" ng-minlength="5" required="required"/>
    </div>
    <div class="form-group">
        <label for="category" class="col-sm-3 control-label">Category</label>
        <select id="category" class="form-control" ng-model="task.category" ng-options="c as c for c in categories">
            <option value=""></option>
        </select>
    </div>
    <div class="form-group">
        <label for="complexity" class="col-sm-3 control-label">Complexity</label>
        <span uib-rating ng-model="task.complexity" max="5" aria-labelledby="default-rating"></span>
    </div>
    <div class="form-group">
        <label for="notes" class="col-sm-3 control-label">Notes</label>
        <textarea id="notes" class="form-control" ng-model="task.notes" rows="3"></textarea>
    </div>
    <div class="form-group">
        <label for="dueDate" class="col-sm-3 control-label">Due date</label>
        <p class="input-group">
            <input type="text" class="form-control" uib-datepicker-popup="{{'dd/MM/yyyy'}}" ng-model="dt" is-open="opened" datepicker-options="dateOptions" close-text="Close" />
            <span class="input-group-btn">
                <button type="button" class="btn btn-default" ng-click="opened = !opened"><i class="glyphicon glyphicon-calendar"></i></button>
            </span>
        </p>
    </div>
</div>