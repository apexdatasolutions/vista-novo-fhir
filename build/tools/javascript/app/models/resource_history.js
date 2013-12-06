var mongoose = require('mongoose');
var _ = require('underscore');

var ResourceHistorySchema = new mongoose.Schema({
  resourceType: String,
  history: [{resourceId: mongoose.Schema.Types.ObjectId, createdAt: Date}]
});

ResourceHistorySchema.methods = {
  addVersion: function (resourceId) {
    this.history.push({resourceId: resourceId, createdAt: Date.now()});
  },

  latestVersionId: function () {
    return _.last(this.history).resourceId;
  }
};

mongoose.model('ResourceHistory', ResourceHistorySchema);