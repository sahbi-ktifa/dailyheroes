<div class="task-form">
    <div class="form-group">
        <label for="name" class="col-sm-3 control-label" translate>Name *</label>
        <input type="text" id="name" class="form-control" ng-model="task.name" ng-minlength="5" required="required"/>
    </div>
    <div class="form-group">
        <label for="category" class="col-sm-3 control-label" translate>Category</label>
        <input type="text" ng-if="edit" class="form-control" ng-model="task.category" readonly="readonly"/>
        <select id="category" class="form-control" ng-model="task.category" ng-options="c as c for c in categories" ng-if="!edit">
            <option value=""></option>
        </select>
    </div>
    <div class="form-group">
        <label for="complexity" class="col-sm-3 control-label" translate>Complexity</label>
        <span uib-rating ng-model="task.complexity" max="5" aria-labelledby="default-rating" read-only="true" ng-if="edit"></span>
        <span uib-rating ng-model="task.complexity" max="5" aria-labelledby="default-rating" ng-if="!edit"></span>
    </div>
    <div class="form-group">
        <label for="notes" class="col-sm-3 control-label">Notes</label>
        <textarea id="notes" class="form-control" ng-model="task.notes" rows="3"></textarea>
    </div>
    <div class="form-group">
        <label for="dueDate" class="col-sm-3 control-label" translate>Due date</label>
        <p class="input-group">
            <input type="text" class="form-control" uib-datepicker-popup="{{'dd/MM/yyyy'}}" ng-model="task.dueDate" is-open="opened" datepicker-options="dateOptions"
                   close-text="{{'Close' | translate}}" current-text="{{'Today' | translate}}" clear-text="{{'Clear' | translate}}" />
            <span class="input-group-btn">
                <button type="button" class="btn btn-default" ng-click="opened = !opened"><i class="glyphicon glyphicon-calendar"></i></button>
            </span>
        </p>
    </div>
    <div class="form-group" ng-if="task.dueDate">
        <label for="notes" class="col-sm-3 control-label" translate>Redundancy</label>
        <label class="btn btn-primary" ng-model="task.redundancy" uib-btn-radio="'daily'" translate>Daily</label>
        <label class="btn btn-primary" ng-model="task.redundancy" uib-btn-radio="'weekly'" translate>Weekly</label>
        <label class="btn btn-primary" ng-model="task.redundancy" uib-btn-radio="'monthly'" translate>Monthly</label>
    </div>
</div>