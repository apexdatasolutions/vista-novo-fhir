var mongoose = require('mongoose');
var _ = require('underscore');
var <ModelName> = mongoose.model('<ModelName>');
var ResourceHistory = mongoose.model('ResourceHistory');
var ResponseFormatHelper = require(__dirname + '/../../lib/response_format_helper');

exports.load = function(req, res, next, id, vid) {
  ResourceHistory.findOne(id, function(rhErr, resourceHistory) {
    if (rhErr) {
      return next(rhErr);
    }
    if(resourceHistory !== null) {
      req.resourceHistory = resourceHistory;
      var lookUpId = null;
      if (vid !== null) {
        lookUpId = vid;
      } else {
        lookUpId = resourceHistory.latestVersionId();
      }
      <ModelName>.findOne(lookUpId, function(modelErr, <LowerCaseModelName>) {
        if (modelErr) {
          return next(modelErr);
        }
        if(<LowerCaseModelName> !== null) {
          req.<LowerCaseModelName> = <LowerCaseModelName>;
          return next();
        }
        else {
          return next(new Error('<ModelName> not found'));
        }
      });
    }
    else {
      return next(new Error('Could not find any resource history'));
    }        
  });
};

exports.show = function(req, res) {
  var <LowerCaseModelName> = req.<LowerCaseModelName>;
  var locals = {<LowerCaseModelName>: <LowerCaseModelName>};
  res.format(ResponseFormatHelper.buildFormatHash('<LowerCaseModelName>', locals, res));
};

exports.create = function(req, res) {
  var <LowerCaseModelName> = new <ModelName>(req.body);
  <LowerCaseModelName>.save(function(err, saved<ModelName>) {
    if(err) {
      res.send(500);
    } else {
      var resourceHistory = new ResourceHistory({resourceType: '<ModelName>'});
      resourceHistory.addVersion(saved<ModelName>.id);
      resourceHistory.save(function(rhErr, savedResourceHistory){
        if (rhErr) {
          res.send(500);
        } else {
          res.set('Location', ("http://localhost:3000/<LowerCaseModelName>/@" + resourceHistory.id));
          res.send(201);
        }
      });
    }
  });
};

exports.update = function(req, res) {
  var <LowerCaseModelName> = req.<LowerCaseModelName>;
  <LowerCaseModelName> = _.extend(<LowerCaseModelName>, req.body);
  <LowerCaseModelName>.save(function(err, saved<LowerCaseModelName>) {
    if(err) {
      res.send(500);
    } else {
      var resourceHistory = req.resourceHistory;
      resourceHistory.addVersion(saved<LowerCaseModelName>);
      resourceHistory.save(function(rhErr, savedResourceHistory) {
        if (rhErr) {
          res.send(500);
        } else {
          res.send(200);
        }
      });
    }
  });
};

exports.destroy = function(req, res) {
  var <LowerCaseModelName> = req.<LowerCaseModelName>;
  <LowerCaseModelName>.remove(function (err) {
    if(err) {
      res.send(500);
    } else {
      res.send(204);
    }
  });
};